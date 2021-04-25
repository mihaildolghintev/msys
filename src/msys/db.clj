(ns msys.db
  (:require [monger.core :as mg]
            [mount.core :as mount]
            ))

(declare db-conn)

(defn get-db []
  (mg/get-db db-conn "msysdb"))




(mount/defstate db-conn :start (mg/connect)
  :stop (mg/disconnect db-conn))

