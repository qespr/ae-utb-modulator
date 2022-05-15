(ns modulator.number-utils
  (:gen-class))


(defn do-binarky
  "Převede Desítkové číslo do binárky"
  ([cislo] (do-binarky cislo nil  ""))
  ([cislo zbytek ret]
   (if (> cislo 0)
     (recur (quot cislo 2) (mod cislo 2) (str zbytek ret))
     ;;Způsob jakým předáváem způsobuje že se bin čísla zapisují až v další
     ;;Iteraci, proto je potřeba provézt poslední zápis zde
     (str zbytek ret))))
 ;; todo: přepsat na test
(do-binarky 16)

(defn zarovnej-na-byte
  "Zarovná binární číslo na specifikovanou pevnou délku v bajtech"
  ([bin-str] (zarovnej-na-byte bin-str 1))
  ([bin-str na-bajt]
   (str (apply str
          ;;Vygeneruje tolik nul kolik je potřeba aby jsme
          ;;se zarovnaly na cílový bajt
          (repeat (-
                   (* 8 na-bajt)
                   (count bin-str))
                  "0"))
        bin-str)))
(zarovnej-na-byte "000") ;;todo: přepsat na test

(defn znak-to-bin
  "Převede znak na jeho binární ASCII reprezentaci ve formě stringu"
  [znak]
  (zarovnej-na-byte (do-binarky (int znak))))

(defn text-to-bin
  "Převede string na jeho binární ASCII reprezentaci"
  [text]
  (apply str (map znak-to-bin text)))