package com.example.appstorydicoding

import com.example.appstorydicoding.response.*

object DataDummy {

    fun generateDummyStoryResponse(): List<ListStory> {
        val items: MutableList<ListStory> = arrayListOf()
        for (i in 0..100) {
            val story = ListStory(
                i.toString(),

                "photo + $i",
                "created + $i",
                "name + $i",
                "description + $i",
                i.toDouble(),
                i.toDouble(),

            )
            items.add(story)
        }
        return items
    }

    fun generateDummyLogin(): Login {
        return Login(
            User(
                "name",
                "id",
                "token"
            ),
            false,
            "token"
        )
    }

    fun generateDummySignUp(): SignUp {
        return SignUp(
            false,
            "success"
        )
    }

    fun generateDummyLocationStory(): StoryResponse {
        val items: MutableList<ListStory> = arrayListOf()
        for (i in 0..100) {
            val story = ListStory(
                i.toString(),
                "photo + $i",
                "created + $i",
                "name + $i",
                "description + $i",
                i.toDouble(),
                i.toDouble()
            )
            items.add(story)
        }
        return StoryResponse(
            items,
            false,
            "success"
        )
    }

    fun generateDummyAddStory(): AddStory {
        return AddStory(
            false,
            "success"
        )
    }
}