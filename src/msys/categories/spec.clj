(ns msys.categories.spec
  (:require [clojure.spec.alpha :as s]
            [msys.utils.validator :refer [problem->text]]))

(s/def :subcategory/_id string?)
(s/def :subcategory/name string?)
(s/def :subcategory/id-map-key keyword?)
(s/def :subcategory/subcategory (s/keys :req-un [:subcategory/name]
                                        :opt-un [:subcategory/_id]))

(s/def :category/_id string?)
(s/def :category/name (s/and  string? not-empty))
(s/def :category/subcategories (s/coll-of :subcategory/name))

(s/def :category/category (s/keys :req-un [:category/name]
                                  :opt-un [:category/_id :category/subcategories]))

(s/def :category/categories (s/coll-of :category/category))

(defmethod problem->text :category/_id [_]
  "Wrong ID. Maybe it must be a string?")

(defmethod problem->text :category/name [_]
  "Category name must be a string")


(defmethod problem->text :category/categories [_]
  "Wrong collection of categories")

(defmethod problem->text :subcategory/id-map-key [_]
  "Subcategory ID map key must be a keyword")

(defmethod problem->text :subcategory/_id [_]
  "Wrong subcategory ID. Maybe it must be a string?")

(defmethod problem->text :subcategory/name [_]
  "Subcategory name must be a string")




