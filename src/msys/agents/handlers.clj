(ns msys.agents.handlers
  (:require [msys.agents.db :as adb]
            [clojure.spec.alpha]))




(defn get-agents [_request]
  (try
    {:status 200
     :body (adb/get-agents)}
    (catch Exception e
      {:status 500
       :body (ex-message e)})))

(defn create-agent [request]
  (let [agent (get-in request [:body :agent])]
    (try
      {:status 200
       :body (adb/create-agent agent)}
      (catch Exception e
        {:status 500
         :body {:error true
                :message (ex-message e)}}))))

(defn update-agent [request]
  (let [agent (get-in request [:body :agent]) ]
    (try
      {:status 200
       :body (adb/update-agent agent)}
      (catch Exception e
        {:status 500
         :body {:error true
                :message (ex-message e)}}))))
