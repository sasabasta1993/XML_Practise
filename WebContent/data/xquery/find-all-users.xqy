xquery version "1.0-ml";

(: Retrieves documents from a collection :)
for $doc in fn:collection("/example")
return $doc