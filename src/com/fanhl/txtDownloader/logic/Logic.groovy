package com.fanhl.txtDownloader.logic

import com.fanhl.txtDownloader.converter.BaseConverter
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

/**
 * Created by fanhl on 15/7/2.
 */
class Logic {
    public static final String outputFolder = "outputTxt"

    void run(BaseConverter converter) {
        println "start"

        List<ArrayList<String>> titleAndUrls = menuUrlToItems(converter)


        titleAndUrls.each {
            println "start to save file:${it[0]}"
            saveFile(it)
        }
//        saveFile(titleAndUrls[0])

        println "end"
    }

    private void saveFile(ArrayList<String> titleAndUrl) {
        def itemUrl = titleAndUrl[1]

        def contentHtmlStr = url2htmlStr(itemUrl)
//        println contentHtmlStr

        Document doc = Jsoup.parse(contentHtmlStr)
//        println doc

        Element element = doc.select("div#content").first()
        String text = element.text()
//        println text

        def folder = new File(outputFolder)
//        if (!folder.exists()) {
//            folder.mkdirs()
//        }
        def file = new File(outputFolder + "/" + titleAndUrl[0] + ".txt")
        file.createNewFile()
        file.withPrintWriter { it.print(text) }
    }

    private String html2Txt(String html) {
        html.replaceAll(/<br\s*[\/]?>/, "\n")
                .replace("&nbsp;", " ")
    }

    /**
     * 返回 [[标题,url],[标题,url],[标题,url],...]
     * @param converter
     * @return
     */
    private List<ArrayList<String>> menuUrlToItems(BaseConverter converter) {
        def htmlStr = url2htmlStr(converter.menuUrl)
        Document doc1 = Jsoup.parse(htmlStr)
        Element masthead = doc1.select("div#mainBox").first()
        Elements links = masthead.select("a[href]")
        def linksStr = links.collect {
            [it.text(), it.attr("href")]
        }
        def titleAndUrls = linksStr.collect { [it[0], converter.menuUrl.replace("index.html", it[1])] }
        titleAndUrls
    }


    String url2htmlStr(String url) { url.toURL().getText("GBK") }
}
