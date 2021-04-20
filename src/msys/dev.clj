(ns msys.dev
  (:require [mount.core :as mount]
            [msys.www]
            [msys.db]))


(defn start []
  (mount/start #'msys.www/www-conn
               #'msys.db/db-conn))

(defn stop []
  (mount/stop))


(comment
  (start)
  (stop))
