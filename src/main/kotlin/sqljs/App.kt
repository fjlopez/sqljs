package sqljs

import kotlinx.html.js.onChangeFunction
import org.w3c.dom.HTMLTextAreaElement
import react.*
import react.dom.*
import kotlin.browser.document

interface AppState : RState {
    var db: Database?
    var error: Throwable?
    var results: Array<QueryResults>?
}

class App : RComponent<RProps, AppState>() {
    override fun componentDidMount() {
        initSqlJs().then { sqljs ->
            setState {
                db = sqljs.Database()
            }
        }.catch { throwable ->
            setState { error = throwable }
        }
    }

    fun exec(sql: String) {
        setState {
            try {
                results = state.db?.exec(sql)
                error = null
            } catch (throwable: Throwable) {
                error = throwable
            }
        }
    }

    fun RBuilder.renderResults(results: Array<QueryResults>): ReactElement = results.lastOrNull()?.let { result ->
        table {
            thead {
                tr {
                    result.columns.forEach { columnName ->
                        td { +columnName }
                    }
                }
            }
            tbody {
                result.values.forEach { row ->
                    tr {
                        row.forEach { value -> td { +value.toString() } }
                    }
                }
            }
        }
    } ?: table {
        thead {
            tr {
                td {
                    +"No results"
                }
            }
        }
    }

    override fun RBuilder.render() {
        if (state.db == null) {
            pre { +"Loading ..." }
            return
        }
        div("App") {
            h1 {
                +"Kotlin-React SQL interpreter"
            }
            textArea {
                attrs {
                    placeholder = "Enter some SQL. No inpiration ? Try \"select sqlite_version()\""
                    onChangeFunction = { event -> exec((event.target as HTMLTextAreaElement).value) }
                }
            }
            pre("error") {
                +(state.error?.toString() ?: "")
            }
            pre {
                state.results?.let {
                    renderResults(it)
                }
            }
        }
    }
}

fun main() {
    js("require('styles.css');")
    render(document.getElementById("root")) {
        child(App::class) {}
    }
}
