(ns msys.categories.handlers
  (:require [msys.categories.db :as cdb]
            [clojure.spec.alpha]))

(defn get-categories [_request]
  (try
    {:status 200
     :body (cdb/get-categories)}
    (catch Exception e
      {:status 500
       :body (ex-message e)})))

(defn create-category [request]
  (let [category-name (get-in request [:body :category-name])]
    (try
      {:status 200
       :body (cdb/create-category category-name)}
      (catch Exception e
        {:status 500
         :body {:error true
                :message (ex-message e)}}))))

(defn remove-category [request]
  (let [category-id (get-in request [:body :category-id])]
    (try
      (cdb/remove-category category-id)
      {:status 200
       :body "OK"}
      (catch Exception e
        {:status 500
         :body {:error true
                :message (ex-message e)}}))))

(defn update-category [request]
  (let [category-id (get-in request [:body :category-id])
        category-name (get-in request [:body :category-name])]
    (try

      {:status 200
       :body (cdb/update-category category-id category-name)}
      (catch Exception e
        {:status 500
         :body {:error true
                :message (ex-message e)}}))))

(defn create-subcategory [request]
  (let [category-id (get-in request [:body :category-id])
        subcategory-name (get-in request [:body :subcategory-name])]
    (try

      {:status 200
       :body (cdb/create-subcatetory category-id subcategory-name)}
      (catch Exception e
        {:status 500
         :body {:error true
                :message (ex-message e)}}))))

(defn update-subcategory [request]
  (let [category-id (get-in request [:body :category-id])
        old-subcategory-name (get-in request [:body :old-subcategory-name])
        subcategory-name (get-in request [:body :subcategory-name])]
    (try

      {:status 200
       :body (cdb/update-subcategory category-id old-subcategory-name subcategory-name)}
      (catch Exception e
        {:status 500
         :body {:error true
                :message (ex-message e)}}))))
