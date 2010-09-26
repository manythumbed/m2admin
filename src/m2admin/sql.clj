(ns m2admin.sql
	(:use [clojure.contrib.sql :as sql :only ()]))

(def connection {:classname "org.sqlite.JDBC",
								 :subprotocol "sqlite",
								 :subname "../simple.sqlite"})

(defn- all-from-table [t c]
	(sql/with-connection c
		(sql/with-query-results results [(str "select * from " t)]
			(doall results))))

(defn servers [c]
	(all-from-table "server" c))

