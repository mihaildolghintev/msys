(ns msys.utils.validator)

(defmulti problem->text
  (fn [spec]
    (let [spec-map (-> spec :clojure.spec.alpha/problems first)
          via (:via spec-map)]
      (peek via))))


(defmethod problem->text :default [_]
  "Something wrong")

(defmethod problem->text nil [_]
  nil)


