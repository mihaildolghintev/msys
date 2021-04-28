(ns msys.categories-test
  (:require  
   [clojure.test :as t]
   [msys.categories.spec]
   [msys.www :refer [app]]
   [cheshire.core :as json]
   [ring.mock.request :as mock]
   [clojure.spec.alpha :as s]))


(defn create-category-request [params]
  (-> (mock/request :post "/categories/create")
      (mock/json-body params)))

(defn remove-category-request [id]
  (-> (mock/request :post "/categories/remove")
      (mock/json-body {:category-id id})))


(t/deftest test-create-category 
  (let [request (create-category-request {:category-name "fish"}) 
        response (app request) 
        category (-> response
                 :body
                 (json/parse-string true)
                 )
        ]
    (t/is (s/valid? :category/category category))
    (app (remove-category-request (:_id category)))
    ))
