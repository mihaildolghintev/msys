(ns msys.categories.routes
  (:require [compojure.core :as c]
            [msys.categories.handlers :as handlers]))

(c/defroutes category-routes
  (c/context "/categories" []
             (c/GET "/" request (handlers/get-categories request))
             (c/POST "/create" request (handlers/create-category request))
             (c/POST "/update-category" request (handlers/update-category request))
             (c/POST "/create-subcategory" request (handlers/create-subcategory request))
             (c/POST "/update-subcategory" request (handlers/update-subcategory request))))


