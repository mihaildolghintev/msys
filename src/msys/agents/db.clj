(ns msys.agents.db
  (:require [msys.db :as db]
            [msys.agents.spec]
            [msys.utils.validator :refer [problem->text]]
            [clojure.spec.alpha :as s]
            [monger.collection :as mc])
  (:import org.bson.types.ObjectId))

(defn agent-exists? [{:keys [by value]}]
  (not
   (nil?
    (mc/find-one-as-map (db/get-db) "agents"
                        (case by
                          :id {:_id (ObjectId. value)}
                          :name {:name value})))))

(defn get-agents []
  {:post [(or (s/valid? :agent/agents %)
              (throw (ex-info (problem->text (s/explain-data :agent/agents %)) {})))]}
  (->> (mc/find-maps (db/get-db) "agents")
       (map #(update % :_id str))))

(defn create-agent [agent]
  {:pre [(or (s/valid? :agent/agent agent)
             (throw (ex-info (problem->text (s/explain-data :agent/agent agent))
                             {})))]
   :post (s/valid? :agent/agent agent)}

  (if (agent-exists? {:by :name
                      :value (:name agent)})
    (throw (ex-info "This agent already exists" {}))
    (-> (mc/insert-and-return (db/get-db) "agents" (assoc agent :balance 0))
        (update :_id str))))

(defn update-agent [agent]
  {:pre [(or (s/valid? :agent/agent agent)
             (throw (ex-info (problem->text (s/explain-data :agent/agent agent))
                             {})))]
   :post (s/valid? :agent/agent agent)}
  (cond
    (agent-exists? {:by :id
                    :value (:_id agent)})
    (-> (mc/save-and-return (db/get-db) "agents"
                            (update agent :_id #(ObjectId. %)))
        (update :_id str))
    :else (throw (ex-info "Category not found" {}))))




