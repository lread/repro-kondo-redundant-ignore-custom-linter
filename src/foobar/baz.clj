(ns foobar.baz)

;; a silly little macro to demonstrate a clj-kondo behaviour
(defmacro dingo [something & body]
  `(let [~something 42]
     ~@body))

;; our clj-kondo hook adds a finding when `something` is `foo`

;; no problem, `something` is not `foo`
(dingo bar (str bar))
;; => "42"

;; problem, `something` is `foo`, clj-kondo should not report the problem because we've
;; ignored it
#_{:clj-kondo/ignore [:acme/foo-not-good]}
(dingo foo (str foo))
;; => "42"

;; problem, `something` is `foo`, clj-kondo should report the problem
(dingo foo (str foo))
;; => "42"

;; simpler case, explore a finding from a built-in linter (instead of custom linter from hook)

;; problem, unused binding `moodog`
(dingo moodog (println "hello"))

;; unused binding supressed
#_{:clj-kondo/ignore [:unused-binding]}
(dingo moodog (println "hello"))
