(ns m2admin.web
	(:use [ring.util.response :only (response)])
	(:use [ring.middleware file])
	(:use [ring.adapter.jetty])
	(:use [net.cgrand.moustache])
	(:use [net.cgrand.enlive-html]))

(def column-labels ["X" "Y" "Z" "P" "Q" "R"])

(def column-head [:table.data :thead :tr :th]) 

(def base-html (html-resource "templates/test.html"))
(def header (select base-html [:thead first-child :th]))
(def header-label 
	(select base-html [[:thead :tr (nth-of-type 1)] :> first-child]))

(defsnippet first-thead-row "templates/table.html"
	[[:thead :tr (nth-of-type 1)]]
	[value]
	[:th] (content value))

(defsnippet table-header-label "templates/base.html"
	[[:tr (nth-of-type 1)] :> first-child]
	[value]
	[:th] (content value))

(defsnippet cell-snippet "templates/test.html" 
	[[:tr (nth-of-type 1)] :> first-child]
	[value]
	[:td] (do->
					(content value)
					(set-attr :id value)))

(defsnippet fill-row "templates/test.html" [[:table :tbody (has [:tr])]]
	[values]
	[:tr] (content (map #(cell-snippet %) values))) 

(defsnippet row-snippet "templates/base.html" [[:tr (has [:td])]]
	[[v1 v2 v3]]
	[[:td (nth-child 1)]] (content v1)
	[[:td (nth-child 2)]] (content v2)
	[[:td (nth-child 3)]] (content v3))

(deftemplate base "templates/base.html" [heading]
	[:h1] (content heading)
	[:table.data :thead :tr first-child] 
		(clone-for [l column-labels] (content l))
	[:table.data :tfoot :tr :th] (content "X"))

(defn- render [t]
	(apply str t))

(def render-to-response
	(comp response render))

(def routes (app 
	(wrap-file "public")
	:get [["admin"] (fn [req] (render-to-response (base "m2admin")))]))

(defn start-server [port]
	(run-jetty routes {:port port}))

(defn -main [& [port]]
	(if port
		(start-server (Integer/parseInt port))
		(start-server 8080)))

