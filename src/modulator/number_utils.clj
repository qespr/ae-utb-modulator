(ns modulator.number-utils
  (:gen-class))


(defn do-binarky
  "Převede Desítkové číslo do binárky"
  ([cislo] (do-binarky cislo nil  ""))
  ([cislo zbytek ret]
   (if (> cislo 0)
     (recur (quot cislo 2) (mod cislo 2) (str zbytek ret))
     ;;Způsob jakým předáváme způsobuje že se bin čísla zapisují až v další
     ;;Iteraci, proto je potřeba provézt poslední zápis zde
     (str zbytek ret))))

(defn zkrat-na-byte
  "Při RLL modulaci se někdy string prodlužuje o bity které nejsou
   součástí původní zprávy. Tato funkce je najde a odstraní"
  [bin-msg]
  ;;Todo: Přepsat na if-let?
  (if (not= (mod (count bin-msg) 8) 0)
    (subs bin-msg 0 (- (count bin-msg) (mod (count bin-msg) 8)))
    bin-msg))

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

(defn bin-to-znak
  "Převede binární znak na jeho ASCII reprezentaci"
  [bin-znak]
  (str (char (. Integer parseInt  bin-znak 2))))


(defn bin-to-string
  "Převede binární String do ASCII stringu"
  [bin-str]
  (apply str (map (fn
                    [lst]
                    (bin-to-znak (apply str lst)))
                  (partition 8 bin-str))))

(defn znak-to-bin
  "Převede znak na jeho binární ASCII reprezentaci ve formě stringu"
  [znak]
  (zarovnej-na-byte (do-binarky (int znak))))

(defn text-to-bin
  "Převede string na jeho binární ASCII reprezentaci"
  [text]
  (apply str (map znak-to-bin text)))
