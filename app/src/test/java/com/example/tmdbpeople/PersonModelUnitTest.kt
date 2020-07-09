package com.example.tmdbpeople

import com.example.tmdbpeople.models.PersonModel
import com.example.tmdbpeople.utils.networkutils.Constants
import org.junit.Assert
import org.junit.Test

class PersonModelUnitTest {
    @Test
    fun testPersonModelGender() {
        val personModel = PersonModel()
        personModel.gender = 1
        val gender = personModel.getGenderStringResId()
        Assert.assertEquals(R.string.female, gender)
    }

    @Test
    fun testPersonModelImage() {
        val personModel = PersonModel()
        personModel.profilePath = "testest.jpg"
        val image = personModel.getImageFullPath()
        Assert.assertEquals(Constants.IMAGE_BASE_URL_500W + personModel.profilePath, image)
    }

    @Test
    fun testEquals() {
        val personModel = PersonModel()
        val personModel2 = PersonModel()
        Assert.assertFalse(personModel.equals(personModel2))
    }
}