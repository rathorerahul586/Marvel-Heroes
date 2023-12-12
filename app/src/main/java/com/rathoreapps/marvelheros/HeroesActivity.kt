package com.rathoreapps.marvelheros

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.rathoreapps.marvelheros.databinding.ActivityHeroesBinding
import com.rathoreapps.marvelheros.utils.showHide
import dagger.hilt.android.AndroidEntryPoint

/**
 * Copyright (C) 2023 RathoreApps Inc.
 *
 * Created by Rahul Kumar
 * @Date: 09/12/23
 * @Time: 11:28 am
 * @Email: rathoreapps01@gmail.com
 *
 * Description: Marvel Character Activity.
 */

@AndroidEntryPoint
class HeroesActivity : AppCompatActivity() {
    private val mainViewModel: HeroesViewModel by viewModels()
    private var heroesListAdapter: HeroesListAdapter? = null

    private lateinit var binding: ActivityHeroesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeroesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        mainViewModel.fetchMarvelCharacters()
        observeMarvelCharacters()
        observeApiError()
        showHideProgressBar(true, showError = false)
    }

    private fun initRecyclerView() {
        binding.heroesListRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        heroesListAdapter = HeroesListAdapter()
        binding.heroesListRv.adapter = heroesListAdapter
    }

    private fun observeMarvelCharacters() {
        mainViewModel.getMarvelCharacters().observe(this) {
            heroesListAdapter?.setCharacterList(it)
            showHideProgressBar(false, showError = false)
        }
    }

    private fun observeApiError() {
        mainViewModel.getErrorText().observe(this) {
            binding.errorText.text = it
            showHideProgressBar(false, showError = true)
        }
    }

    private fun showHideProgressBar(show: Boolean, showError: Boolean) {
        with(binding) {
            progressBar.showHide(show)
            heroesListRv.showHide(!show && !showError)
            errorText.showHide(!show && showError)
        }
    }
}