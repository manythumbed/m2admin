(ns m2admin.web
	(:use [ring.util.response :only (response)])
	(:use [ring.middleware file])
	(:use [ring.adapter.jetty])
	(:use [net.cgrand.moustache])
	(:use [net.cgrand.enlive-html])
	(:use [m2admin.table]))

(def primary-columns ["A" "B" "C"])
(def secondary-columns ["a" "b" "c"])
(def data 
	[ ["1" "2" "3"]
		["4" "5" "6"]
		["7" "8" "9"] ])

(deftemplate base "templates/base.html" [heading]
	[:h1] (content heading)
	[:table] (content
		(header-row primary-columns secondary-columns)
		(table-data data)
		(footer-row primary-columns secondary-columns)))

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

