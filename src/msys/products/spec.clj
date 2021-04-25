(ns msys.products.spec
  (:require [clojure.spec.alpha :as s]
            [clojure.string :as str]
            [msys.categories.spec]
            [msys.agents.spec]
            [msys.utils.validator :refer [problem->text]]))

(s/def ::not-empty-string (s/and string? not-empty))
(s/def ::max-id-count #(= 10 (count %)))
(s/def ::starts-with-ms #(str/starts-with? % "MS"))

(s/def :price-history/datetime ::not-empty-string)
(s/def :price-history/old-price double?)
(s/def :price-history/new-price double?)
(s/def :price-history/price-history (s/keys :req-un [:price-history/datetime
                                                     :price-history/old-price
                                                     :price-history/new-price]))

(s/def :product-history/datetime ::not-empty-string)
(s/def :product-history/document-type ::not-empty-string) ;; TODO: Change to enum
(s/def :product-history/document-id ::not-empty-string)
(s/def :product-history/product-history (s/keys :req-un [:product-history/datetime
                                                         :product-history/document-type
                                                         :product-history/document-id]))

(s/def :product/_id (s/and string? ::max-id-count ::starts-with-ms))
(s/def :product/category :category/category)
(s/def :product/subcategory string?)
(s/def :product/title ::not-empty-string)
(s/def :product/buy-price (s/and double? #(>= % 0)))
(s/def :product/sell-price (s/and double? #(>= % 0)))
(s/def :product/unit-type #{"kg" "p"})
(s/def :product/vat #(<= 0 % 100))
(s/def :product/plu #(>= % 0))
(s/def :product/cash #(>= % 0))
(s/def :product/barcode ::not-empty-string)
(s/def :product/barcodes (s/coll-of :product/barcode))
(s/def :product/agents (s/coll-of :agent/agent))
(s/def :product/history (s/coll-of :product-history/product-history))
(s/def :product/price-history (s/coll-of :price-history/price-history))

(defmethod problem->text ::max-id-count [_]
  "ID length must be 10 characters")

(defmethod problem->text ::starts-with-ms [_]
  "Invalid ID. ID must start with MS")

(defmethod problem->text :product/_id [_]
  "Invalid ID. ID must be a string")

(defmethod problem->text :product/subcategory [_]
  "Invalid Subcategory. Subcategory must be a non empty string")

(defmethod problem->text :product/title [_]
  "Invalid Title. Title must be a non empty string")

(defmethod problem->text :product/buy-price [_]
  "Invalid Buy Price. Buy Price must be a double type and positive number")

(defmethod problem->text :product/sell-price [_]
  "Invalid Sell Price. Sell Price must be a double type and positive number")

(defmethod problem->text :product/unit-type [_]
  "Invalid unit type. Unit type of product can only be (kg - kilogramm) or (p - piece)")

(defmethod problem->text :product/vat [_]
  "Invalid VAT. VAT must be between 0 and 100")

(defmethod problem->text :product/plu [_]
  "Invalid PLU. PLU must be positive number")

(defmethod problem->text :product/cash [_]
  "Invalid CASH. CASH must be positive number")

(defmethod problem->text :product/barcode [_]
  "Invalid Barcode. Barcode must be a non empty string")

(defmethod problem->text :product/barcodes [_]
  "Barcodes should only contain a collection of barcodes")

(defmethod problem->text :product/agents [_]
  "Agents should only contain a collection of agents")

(defmethod problem->text :product/history [_]
  "History should only contain a collection with this product operations")

(defmethod problem->text :product/price-history [_]
  "Price History should only contain a collection with this product price change operations")

;; (problem->text  (s/explain-data :product/category {:name 3}))



