(ns org.acme
  (:require [clj-kondo.hooks-api :as api]))

(defn dingo
  "Let's pretend we have `(dingo some-sym some-body)` macro that is expands to (let [some-sym 42] some-body)
  And let's say if some-body is :foo we have non-fatal finding, and we also return a node."
  [{:keys [node]}]
  (let [[somesym & somebody] (rest (:children node))]
    (when (= (str somesym) "foo")
      (api/reg-finding! (assoc (meta somesym)
                               :message "How dare foo!"
                               :type :acme/foo-not-good)))
    (let [new-node (api/list-node
                     (list*
                       (api/token-node 'let)
                       (api/vector-node [somesym (api/token-node 42)])
                       somebody))]
      {:node new-node})))
