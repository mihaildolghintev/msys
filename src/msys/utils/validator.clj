(ns msys.utils.validator
  (:require [clojure.spec.alpha :as s]))

(defmulti problem->text
  (fn [spec]
    (let [spec-map (-> spec :clojure.spec.alpha/problems first)
          via (:via spec-map)]
      (peek via))))



(defmethod problem->text :default [_]
  "Something wrong")

(defmethod problem->text nil [_]
  nil)

(defn validate [spec value & [data]]
  (or (s/valid? spec value)
      (throw (ex-info (problem->text (s/explain-data spec value)) (if (data)
                                                                    data
                                                                    {})))))

