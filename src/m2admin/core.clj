(ns m2admin.core)

(use 'org.zeromq.clojure)

(defn app [req]
	{:status 200
	 :headers {"Content-Type" "text/html"}
	 :body "Hello from Ring"})

(defn run-mongrel2 [handler options]
	(let [ctx (make-context 1)
				sub (make-socket ctx +sub+)
				pub (make-socket ctx +pub+)]
		(bind sub "tcp://127.0.0.1:5566")
		(.subscribe sub (.getBytes ""))
		(bind pub "tcp://127.0.0.1:5565")))

(run-mongrel2 app {})

