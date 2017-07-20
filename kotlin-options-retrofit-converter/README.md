# Kotlin Options Retrofit 2 Converter

A module which provides a [Retrofit 2](https://github.com/square/retrofit/) `Converter` to allow creation of `Option` instances for calls with a `null` response body.

You can use it to define Retrofit interfaces such as:
 
```kotlin
interface Service {
        @GET("/call") fun option(): Call<Option<MyClass>>
        @GET("/observable") fun option(): Observable<Option<MyClass>>
    }
```
    
Add to your Retrofit instance by creating an instance of `KotlinOptionConverter`:

```kotlin
        val retrofit = Retrofit.Builder()
                /** etc **/
                .addConverterFactory(KotlinOptionConverterFactory.create())
                /** etc **/
                .build()
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
