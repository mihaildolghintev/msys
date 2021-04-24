(ns msys.www
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [mount.core :as mount]
            [compojure.core :as c]
            [msys.categories.routes :refer [category-routes]]
            [msys.handlers.category :as category-handlers]))

(declare www-conn)

(c/defroutes routes
  (c/context "/" []
    (c/GET "/" request {:status 200
                        :body "OKaf"})
    category-routes))

(def app
  (-> routes
      wrap-json-response
      (wrap-json-body {:keywords? true})))

(mount/defstate www-conn :start (run-jetty app {:port 3000 :join? false})
  :stop (.stop www-conn))
