package com.example.tmdbpeople

import com.example.tmdbpeople.models.PersonImage
import com.example.tmdbpeople.utils.networkutils.Constants
import org.junit.Assert
import org.junit.Test

class PersonImageUnitTest {
    @Test
    fun testPersonImageModelGetImage() {
        val personImage = PersonImage()
        personImage.filePath = "testest.jpg"
        val image = personImage.getImageFullPath()
        Assert.assertEquals(Constants.IMAGE_BASE_URL_500W + personImage.filePath, image)
    }
}