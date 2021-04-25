(ns msys.agents.spec
  (:require [clojure.spec.alpha :as s]
            [msys.utils.validator :refer [problem->text]]))

(s/def :agent/_id string?)
(s/def :agent/name (s/and string? not-empty))
(s/def :agent/balance double?)
(s/def :agent/comments string?)
(s/def :agent/phone string?)
(s/def :agent/agent (s/keys :req-un [:agent/name ]
                            :opt-un [:agent/_id :agent/comments :agent/phone :agent/balance]))


(s/def :agent/agents (s/coll-of :agent/agent))

(defmethod problem->text :agent/_id [_]
  "Wrong ID. Maybe it must be a string?")

(defmethod problem->text :agent/name [_]
  "Agent name must be a string")

(defmethod problem->text :agent/balance [_]
  "Balance must be a double number")

(defmethod problem->text :agent/agents [_]
  "Wrong collection of categories")

