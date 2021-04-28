(ns msys.products.db
  (:require [msys.db :as db]
            [clojure.spec.alpha :as s]
            [monger.collection :as mc]))


(defn product-id->info-db [product-id]
  (mc/insert-and-return (db/get-db) "product-id-list" {:product-id product-id}))

(defn plu->info-db [plu]
  (mc/insert-and-return (db/get-db) "plu-list" {:plu plu}))

(defn cash->info-db [cash]
  (mc/insert-and-return (db/get-db "cash-list" {:cash cash})))

(defn <-product-id-list []
  (->> (mc/find-maps (db/get-db) "product-id-list")
       (map :product-id)))

(defn <-plu-list []
  (->> (mc/find-maps (db/get-db) "plu-list")
       (map :plu)))


(defn <-cash-list []
  (->> (mc/find-maps (db/get-db) "cash-list")
       (map :cash)))

(<-product-id-list)


(defn parse-id [str-id]
  (-> str-id
      (subs 2)
      (Integer/parseInt)))

(defn take-free-value [list-id-numbers]
  (cond
    (empty? list-id-numbers) 1
    :else (->> list-id-numbers
               sort
               (reduce (fn [acc x] (if (= (inc (last acc)) x)
                                     (conj acc x)
                                     acc)) [(first list-id-numbers)])
               last
               inc)))



(defn take-unique-id [ids]
  (->> ids 
       (map parse-id)
       take-free-value
       ))

(defn generate-product-id []
  (let [number (take-unique-id (<-product-id-list))
        need-zero (- 10 (count (str number)) 2)]
    (str "MD" (apply str (repeat need-zero 0)) number)))

(defn generate-cash []
  (let [cash-list (<-cash-list)]
    (take-unique-id cash-list)))

(defn generate-plu []
  (let [plu-list (<-plu-list)]
    (take-unique-id plu-list)))

