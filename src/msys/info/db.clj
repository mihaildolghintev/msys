(ns msys.info.db
  (:require [monger.collection :as mc]
            [clojure.string :as str]
            [msys.db :as db]
            [clojure.spec.alpha :as s]))


(defn product-id->info-db [product-id]
  (mc/insert-and-return (db/get-db) "product-ids" {:product-id product-id}))

(defn <-product-ids []
  (->> (mc/find-maps (db/get-db) "product-ids")
       (map :product-id)))



(defn parse-id [str-id]
  (-> str-id
      (subs 2)
      (Integer/parseInt)))

(defn take-free-id [list-id-numbers]
  (->> list-id-numbers
      sort
      (reduce (fn [acc x] (if (= (inc (last acc)) x) (conj acc x) acc)) [(first list-id-numbers)])
      last
      inc))

(defn take-unique-id [ids]
  (->> ids 
       (map parse-id)
       take-free-id
       ))

(defn generate-product-id []
  (let [number (take-unique-id (<-product-ids))
        need-zero (- 10 (count (str number)) 2)]
    (str "MD" (apply str (repeat need-zero 0)) number)))

