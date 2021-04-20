(ns msys.db
  (:require [monger.core :as mg]
            [mount.core :as mount]))

(mount/defstate db-conn :start (mg/connect)
  :stop (mg/disconnect db-conn))



