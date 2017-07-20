# Kotlin Options Assertions

Use these assertions to test `Option` values in a more readable way.

Rather than force extracting the value, like this:

```Kotlin
   val someOption = optionOf("abc")
   assertTrue(someOption.getUnsafe() == "abc") 
```

You can test your Options using: 

```Kotlin
    val someOption = optionOf("abc") 
    assertThat(someOption).hasValue("abc")
```

or directly:

```Kotlin
    val someOption = optionOf("abc")  
    someOption.assertHasValue("abc")
```
    
You can also use a predicate:

```Kotlin
    val someOption = optionOf("abc")   
    someOption.assertHasValue { it.length > 2 }
```

## Acknowledgements

Brought to you by the power of the [Chilicorn](http://spiceprogram.org/chilicorn-history/) and the [Futurice Open Source Program](http://spiceprogram.org/).

![Chilicorn Logo](https://raw.githubusercontent.com/futurice/spiceprogram/gh-pages/assets/img/logo/chilicorn_no_text-256.png)
## License

    Copyright 2017 Peter Tackage

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
