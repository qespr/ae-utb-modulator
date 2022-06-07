(ns modulator.core
  (:require [modulator.number-utils :as nu]
            [clojure.string :as cstr]
            [clojure.set])
  (:gen-class))

;;Kódovací tabulky
(def RLL-1
  "První verze RLL modulace jak je definováno v návodu na cvičení"
  {"00" "PNNN",
   "01" "NPNN",
   "100" "NNPNNN",
   "101" "PNNPNN",
   "111" "NNNPNN",
   "1100" "NNNNPNNN",
   "1101" "NNPNNPNN"})

(def RLL-2
  "Druhá verze RLL modulace jak je definováno v návodu na cvičení"
  {"11" "PNNN",
   "10" "NPNN",
   "011" "NNPNNN",
   "010" "PNNPNN",
   "000" "NNNPNN",
   "0010" "NNPNNPNN",
   "0011" "NNNNPNNN"})

(defn- rll-match
  "Zjistí který segment se nachází jako další v binárním řetězci
  Vrací segment, takže si buď můžete udělat substring pomocí jeho délky;
  nebo jej přímo použít pro lookup."
  ([klice bin-string] (rll-match klice klice bin-string))
  ;;obsahuje navíc kopii klíčů [kk] aby jsme mohli obnovit hledání po přidání nul
  ([klice kk bin-string]
  ;;Je potřeba pohlídat jestli klíče nejsou nenenulové, pak se přidávají až dvě nuly
  (cond
    ;;pokud jsou klíče už prázdné - žádná kombinace se nenašla, musíme přidávat nuly;
    ;;můžou se přidat max 2 nuly ale zde na to sereme, zatím to funguje
    (= klice '()) (recur kk kk (str bin-string "0"))
    (cstr/starts-with? bin-string (first klice)) (first klice)
    :else (recur (rest klice) kk bin-string))))

(defn rll-encode
  "Zakóduje binární string pomocí specifikované RLL tabulky"
  ([tabulka bin-string] (rll-encode tabulka bin-string nil))
  ([tabulka string-rest ret]
   (if (> (count string-rest) 0)
     (let [mezi-klic (rll-match (keys tabulka) string-rest)]
     (recur
      tabulka
      (if (< (count mezi-klic) (count string-rest))
        (subs string-rest (count mezi-klic)) ;;Pokračuje protože je pořád co převádět
        "") ;;Ukončí rekurzy v dalším kole
      (str ret (tabulka mezi-klic))))
     ret)))

(defn- rll-match-absolute
  "Vrátí první klíč který odpovídá začátku daného stringu
  Funguje jako rll-match ale string neprodlužuje o žádné znaky.
  Pokud nenajde ždáný odpovídající klíč, vrací nil

  K použíti s rll-decode"
  [klice modul-string]
  (cond
  (= klice '()) nil
  (cstr/starts-with? modul-string (first klice)) (first klice)
  :else (recur (rest klice) modul-string)))
(rll-match-absolute (keys (clojure.set/map-invert RLL-1)) "P")

(defn rll-decode
  "Dékóduje modulovaný string pomocí specifikované RLL tabulky
  Vrací binární reprezentaci"
  ([tabulka bin-string] (rll-decode (clojure.set/map-invert tabulka) bin-string nil))
  ([tabulka string-rest ret]
   (if (> (count string-rest) 0)
     (let [temp-klic (rll-match-absolute (keys tabulka) string-rest)
           mezi-klic (if (not= nil temp-klic)
                       temp-klic
                       (throw (ex-info
                               "Neplatná modulované sekvence"
                               {:zbyvajici-data string-rest
                                :uspesna-demodulace ret})))]
       (recur
        tabulka
        (if (and (not= (count mezi-klic) 0) (< (count mezi-klic) (count string-rest)))
          (subs string-rest (count mezi-klic))
          "") ;;Ukončí rekurzi v dalším kole
        (str ret (tabulka mezi-klic))))
     (nu/zkrat-na-byte ret))))

(defn mfm-decode
  "Dékoduje MFM modulovaný text, vrací binární string. V případě neplatné modulace vyhodí vyjímku"
  ;;Kvůli konfliktům nelze řešit jen pomocí string-replace
  ([mfm-text] (mfm-decode mfm-text nil))
  ([mfm-text ret]
   (cond
     (>= (count mfm-text) 2) (let [cast (subs mfm-text 0 2)
                                   zbytek (subs mfm-text 2)]
                               (recur zbytek
                                      (str ret
                                           (if
                                               (or (= cast "PN")
                                                   (= cast "NN"))
                                             "0"
                                             "1"))))
     (= (count mfm-text) 1) (throw (ex-info
                                    "Nekompletní modulace!"
                                    {:prebyvajici-data mfm-text
                                     :uspesna-demodulace ret}))
     :else ret)))

;;Komplet pravidla
;;0 = PN if předtím 00
;;0 = NN if předtím 10
;;1 = NP
(defn mfm-encode
  "Zakóduje binární text pomocí MFM modulace"
  ([bin-text] (mfm-encode (first bin-text) \0 (rest bin-text) nil))
  ([bit prev tail ret]
   (if (not= bit nil) ;;Dokud máme bity ke kontrole
     (recur
      (first tail) ;;Předáváme další bit na průzkum
      bit ;;Předchozí bude současný bit
      (rest tail) ;;Ocas bude ocas ocasu
      (str ret
           ;;Hlavní logika kde se rozhoduje o tom jaká modulace se použije
           (if (= bit \1)
             "NP"
             (if (= prev \0)
               "PN"
               "NN"))))
     ret)))

(defn fm-decode
  "Dékoduje text pomocí FM modulace. Vrací binární reprezentaci"
  [fm-text]
  (cstr/replace
   (cstr/replace fm-text #"PN" "0")
   #"PP" "1"))

(defn fm-encode
  "Zakóduje text pomocí FN modulace"
  [bin-text]
  (cstr/replace
   (cstr/replace bin-text #"0" "PN")
   #"1" "PP"))

(defn get-efektivita
  "Vrátí počet impulzů v modulovaném stringu
  čímž se vyjadřuje efektivita zakódování"
  [modulovany-string]
  ((frequencies modulovany-string) \P))

(defn print-help
  "Vypíše nápovědu"
  []
  (println "Základní použití:")
  (println "modulator [text] - Vypíše všechny modulované verze [text]u, pokud text obsahuje mezery je nutné jej dát do \"uvozovek\"")
  (println "V tomto případě vypíše, počet půlzů, převod do všech modulací + binární reprezentaci textu")
  (println "Pokročilé možnosti:")
  (println "-e [text] - Text k modulování")
  (println "-d [modulovaný text] - text k demodulování, je potřeba upřesnit o jakou modulaci se jedná pomocí -t")
  (println "-t {fm, mfm, rll1, rll2} - Specifikuje o jaký typ modulace se jedná (při -d) nebo jaká se žádá (při -e)")
  (println "-q - Tichý režim, jen vypsat modulaci/demodulaci bez statistik a dalších převodů, funguje pouze pokud je použit '-t'")
  (println "Každé další opakování jakékoliv možnosti přepíše tu předchozí (-t fm -t mfm => platí pouze '-t mfm'"))

(defn do-everything
  "Provede všechny dostupné modulace"
  [text]
  (let [text-bin (nu/text-to-bin text)
        text-fm (fm-encode text-bin)
        text-mfm (mfm-encode text-bin)
        text-rll1 (rll-encode RLL-1 text-bin)
        text-rll2 (rll-encode RLL-2 text-bin)]
    (println "Text: " text)
    (println "   Bin: " text-bin)
    (println "    FM: " text-fm)
    (println " Pulzu: " (get-efektivita text-fm))
    (println "   MFM: " text-mfm)
    (println " Pulzu: " (get-efektivita text-mfm))
    (println " RLL-1: " text-rll1)
    (println " Pulzu: " (get-efektivita text-rll1))
    (println " RLL-2: " text-rll2)
    (println " Pulzu: " (get-efektivita text-rll2))))
