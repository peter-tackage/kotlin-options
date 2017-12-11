# Kotlin Options RxJava 2 Extensions

A module which provides [RxJava 2](https://github.com/ReactiveX/RxJava/) extensions to transform transform RxJava 2 streams, including `Observable`, `Flowable`, `Single` and `Maybe`.

You can use this to filter an `Option` to its value:

```Kotlin
    Observable.just(optionOf("abc"))
        .filterNotNone()
        .subscribe { value : String -> println(value.length) }
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
