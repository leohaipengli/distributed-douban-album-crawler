package com.leoleoli.douban_crawler

import org.jsoup.nodes.Document
import javax.print.Doc


private val numPhotosRegex = "共(\\d+)张照片".toRegex()
private const val numPhotosselector = "#content > div.grid-16-8.clearfix > div.article > div.pl.photitle > span"

private const val metaSelector = "#content > div.grid-16-8.clearfix > div.article > div.sns-bar > div > div.sharing > div.sharing-button > div > div > span > a"
private const val albumNameAttr = "data-name"

private const val albumNameSelectorOnPhotoPage = "head > title"

private const val photosSelector1 = "#content > div.grid-16-8.clearfix > div.article > div.photolst.clearfix > div.photo_wrap"
private const val photosSelector2 = "a"

private const val nextPageSelector = "#content > div.grid-16-8.clearfix > div.article > div.paginator > span.next > a"

private const val photoSelector = "#content > div.grid-16-8.clearfix > div.article > div > div.image-show > div > a > img"


fun getNumPhotosOfAlbum(doc: Document): Int {
    val numPhotosDes = doc.select(numPhotosselector).text()
    return numPhotosRegex.find(numPhotosDes)!!.groups[1]!!.value.toInt()
}


fun getAlbumNameFromPhotoPage(doc: Document): String {
    return doc.select(albumNameSelectorOnPhotoPage).text().split("-").last()
}

fun getAlbumName(doc: Document): String {
    return doc.select(metaSelector).attr(albumNameAttr)
}


fun getPhotoLinks(doc: Document): List<String> {
    val photosDiv = doc.select(photosSelector1)
    return photosDiv.map { it.select(photosSelector2).attr("href") }
}

fun getNextPageLink(doc: Document): String? {
    return doc.select(nextPageSelector).attr("href")
}

fun getPhotoLink(doc: Document): String {
    return doc.select(photoSelector).attr("src")
}
