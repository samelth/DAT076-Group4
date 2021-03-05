module Skrib where

import Text.Parsec(char,spaces,string,noneOf,eof,many,many1,digit,parse)
import Text.Parsec.String(Parser)

data SkribblioWord = SkribblioWord String Double Double Double
data QuickDrawWord = QuickDrawWord String Double

instance Show SkribblioWord where
  show (SkribblioWord s r g b) = s <> ": (" <> show (truncate r) <> "," <> show (truncate g) <> "," <> show (truncate b) <> ")"

instance Show QuickDrawWord where
  show (QuickDrawWord s d) = s <> ": " <> show d

skribblioWord :: Parser SkribblioWord
skribblioWord = do
  _ <- string "rgb("
  r <- many1 digit
  _ <- char ','
  g <- many1 digit
  _ <- char ','
  b <- many1 digit
  _ <- char ')'
  s <- many1 $ noneOf "\n\r"
  _ <- spaces
  return $ SkribblioWord s (read r) (read g) (read b)

skribblioWords :: Parser [SkribblioWord]
skribblioWords = many skribblioWord <* eof

convert :: SkribblioWord -> Maybe QuickDrawWord
convert (SkribblioWord s r g b)
  | r == 0    = Nothing
  | g == 0    = Nothing
  | b /= 0    = Nothing
  | otherwise = Just $ QuickDrawWord s (r / g)

avg xs = sum xs / (read $ show $ length xs)

avg' xs = sum xs' / (read $ show $ length xs') where
  xs' = [r | (QuickDrawWord _ r) <- xs]

stddev xs =
  let a      = avg' xs
      variance   = avg deviations
      deviations = map (\(QuickDrawWord _ r) -> (r - a)^2) xs
  in sqrt variance

sigma x a d = (x - a) / d

filterOutside s xs
  | s < 0     = filter (\(QuickDrawWord _ r) -> sigma r a d <= s) xs
  | s > 0     = filter (\(QuickDrawWord _ r) -> sigma r a d >= s) xs
  | otherwise = xs
  where a = avg' xs
        d = stddev xs

filterInside s xs =
  let a = avg' xs
      d = stddev xs
  in filter (\(QuickDrawWord _ r) -> abs (sigma r a d) < abs s) xs

toSQL :: (String,Int) -> String
toSQL (s,n) = "INSERT INTO APP.DRAWINGWORD (WORD, \"LEVEL\") VALUES ('" <> s <> "', " <> show n <> ");"

main = do
  s <- readFile "skribblioWordList.txt"
  let (Right sws) = parse skribblioWords "" s
      qws         = [qw | (Just qw) <- map convert sws]
      easy        = filterOutside (-0.6) qws
      medium      = filterInside  0.6    qws
      hard        = filterOutside 0.6    qws
      easy'       = [(s,1) | (QuickDrawWord s _) <- easy]
      medium'     = [(s,2) | (QuickDrawWord s _) <- medium]
      hard'       = [(s,3) | (QuickDrawWord s _) <- hard]
      statements  = concatMap (map toSQL) [easy',medium',hard']
  writeFile "statements.txt" $ unlines statements

