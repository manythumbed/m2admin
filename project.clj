(defproject m2admin "1.0.0-SNAPSHOT"
  :description "Simple web admin for Mongrel2"
  :dependencies [[org.clojure/clojure "1.2.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
								 [ring/ring-core "0.3.0-RC2"]
								 [ring/ring-devel "0.3.0-RC2"]
								 [ring/ring-jetty-adapter "0.3.0-RC2"]
								 [net.cgrand/moustache "1.0.0-SNAPSHOT"]
								 [enlive/enlive "1.0.0-SNAPSHOT"]
								 [org.xerial/sqlite-jdbc "3.7.2"]
								 [org.zmq/jzmq "2.0.6-SNAPSHOT"]
								 [org.clojars.mikejs/clojure-zmq "2.0.7-SNAPSHOT"]]
	:dev-dependencies [[lein-run "1.0.0"]]
	:run-aliases {:server [m2admin.web -main]}
	:native-path "/usr/local/lib")
