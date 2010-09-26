(ns m2admin.table
	(:use [net.cgrand.enlive-html]))

(def table-template (html-resource "templates/table.html"))

(defn t [selector]
	(select (html-resource table-template) selector))

(defsnippet header-cell table-template
	[[:thead] [:tr first-child] [:th first-child]]
	[value]
	[:th] (content value))

(defsnippet footer-cell table-template
	[[:tfoot] [:tr first-child] [:th first-child]]
	[value]
	[:th] (content value))
	
(defsnippet table-cell-first table-template
	[[:tbody] [:tr first-child] [:td first-child]]
	[value]
	[:td] (content value))

(defsnippet table-cell-rest table-template
	[[:tbody] [:tr first-child] [:td (nth-child 2)]]
	[value]
	[:td] (content value))
	
(defn- fillrow [values]
	(content 
		(table-cell-first (first values))
		(map #(table-cell-rest %) (rest values))))

(defsnippet header-row table-template
	[[:thead]]
	[primary secondary]
	[:tr] (content (map #(header-cell %) secondary))
	[:tr.main] (content (map #(header-cell %) primary)))

(defsnippet footer-row table-template
	[[:tfoot]]
	[primary secondary]
	[:tr] (content (map #(footer-cell %) secondary))
	[:tr.main] (content (map #(footer-cell %) primary)))

(defsnippet table-row-odd table-template
	[[:tbody] [:tr.odd first-child]]
	[values]
	[:tr] (fillrow values))

(defsnippet table-row-even table-template
	[[:tbody] [:tr.even (nth-child 2)]]
	[values]
	[:tr] (fillrow values))

(defn indexed [coll] (map vector (iterate inc 0) coll))

(defn fill-data [xs]
	(letfn [(indexed [coll] (map vector (iterate inc 0) coll))]
		(map #(if (even? (first %)) 
			(table-row-even (first (rest %)))
			(table-row-odd (first (rest %)))) (indexed xs))))

(defsnippet table-data table-template
	[[:tbody]]
	[data]
	[:tbody] (content (fill-data data)))

(defsnippet table table-template
	[:table]
	[primary secondary data]
	[:table] (content 
		(header-row primary secondary)
		(table-data data)
		(footer-row primary secondary)))

