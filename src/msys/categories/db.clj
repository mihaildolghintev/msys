(ns msys.categories.db
  (:require [msys.db :as db]
            [msys.categories.spec]
            [msys.utils.validator :refer [problem->text validate]]
            [clojure.string :as str]
            [clojure.spec.alpha :as s]
            [monger.operators :refer [$push $set]]
            [monger.collection :as mc])
  (:import org.bson.types.ObjectId))

(defn category-exists? [{:keys [by value]}]
  (not
   (nil?
    (mc/find-one-as-map (db/get-db) "categories"
                        (case by
                          :id {:_id (ObjectId. value)}
                          :name {:name value})))))

(defn subcategory-exists? [category-id subcategory-name]
  (->> (mc/find-one-as-map (db/get-db) "categories"
                           {:_id (ObjectId. category-id)})
       :subcategories
       (filter #(= % subcategory-name))
       seq
       ))

(defn get-categories []
  {:post [(or (s/valid? :category/categories %)
              (throw (ex-info (problem->text (s/explain-data :category/categories %)) {})))]}
  (->> (mc/find-maps (db/get-db) "categories")
       (map #(update % :_id str))))

(defn create-category [category-name]
  {:pre [(or (s/valid? :category/name category-name)
             (throw (ex-info (problem->text (s/explain-data :category/name category-name))
                             {})))]
   :post (s/valid? :category/category %)}

  (if (category-exists? {:by :name
                         :value category-name})
    (throw (ex-info "Category already exists" {}))
    (-> (mc/insert-and-return (db/get-db) "categories" {:name (str/lower-case category-name)})
        (update :_id str))))

(defn remove-category [category-id]
  {:pre [(validate :category/_id category-id)]}
  (mc/remove-by-id (db/get-db) "categories" (ObjectId. category-id)))

(defn create-subcatetory [category-id subcategory-name]
  {:post [(s/valid? :category/category %)]}
  (if (subcategory-exists? category-id subcategory-name)
    (throw (ex-info "Subcategory already exists" {}))
    (do (mc/update (db/get-db) "categories"
                   {:_id (ObjectId. category-id)}
                   {$push {:subcategories (str/lower-case subcategory-name)}})
        (-> (mc/find-one-as-map (db/get-db) "categories"
                                {:_id (ObjectId. category-id)})
            (update :_id str)))))

(defn update-category [category-id new-category-name]
  {:post [(s/valid? :category/category %)]}
  (cond
    (category-exists? {:by :id
                       :value category-id})
    (do (mc/update (db/get-db) "categories"
                   {:_id (ObjectId. category-id)}
                   {$set {:name (str/lower-case new-category-name)}})
        (-> (mc/find-one-as-map (db/get-db) "categories"
                                {:_id (ObjectId. category-id)})
            (update :_id str)))

    :else (throw (ex-info "Category not found" {}))))

(defn update-subcategory [category-id old-subcategory-name subcategory-name]
  {:post [(s/valid? :category/category %)]}
  (let [current-category (mc/find-map-by-id (db/get-db) "categories" (ObjectId. category-id))
        current-subcategories (:subcategories current-category)
        updated-subcategories (map
                               #(if (= % old-subcategory-name) subcategory-name %)
                               current-subcategories)]
    (cond
      (nil? current-category)
      (throw (ex-info "Category not found" {}))
      (not (some #{old-subcategory-name} current-subcategories))
      (throw (ex-info "Subcategory not found" {}))
      :else (do (mc/update (db/get-db) "categories"
                           {:_id (ObjectId. category-id)}
                           {$set {:subcategories updated-subcategories}})
                (-> (mc/find-one-as-map (db/get-db) "categories"
                                        {:_id (ObjectId. category-id)})
                    (update :_id str))))))




