# code2svg-core
Java library that converts code into SVG text with syntax highlighting

### Example Input
```
context iso3166 {

	namespace vo {
		
		import org.fuin.types.*
		import org.fuin.constr.*
		
		/**
		 * ISO 3166-1 alpha-3 three-letter country code defined in ISO 3166-1.
		 */
		value-object Alpha3CountryCode base String {
			
			slabel "ALPHA3CC" // Whatever
			label "Alpha-3 country code"
			tooltip "ISO 3166-1 alpha-3 three-letter country code defined in ISO 3166-1"
			prompt "XXX"
			examples "USA" "FRA" "DEU"
			
			/** Internal value. */
			String value invariants Length(3, 3)
			
		}
			
	}
	
}
```
### Example Output
<img src="http://fuin.org/files/Alpha3CountryCode.ddd.svg" width="100%" height="500">

