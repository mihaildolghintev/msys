(ns msys.agents.routes
  (:require [compojure.core :as c]
            [msys.agents.handlers :as handlers]))

(c/defroutes agents-routes
  (c/context "/agents" []
    (c/GET "/" request (handlers/get-agents request))
    (c/POST "/create" request (handlers/create-agent request))
    (c/POST "/update" request (handlers/update-agent request))))
