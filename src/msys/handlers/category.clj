(ns msys.handlers.category)

(defn categories-list [request]
  (println request)
  {:status 200
   :body [{:id 1 :name "Category one"}
          {:id 2 :name "Category two"}]})
