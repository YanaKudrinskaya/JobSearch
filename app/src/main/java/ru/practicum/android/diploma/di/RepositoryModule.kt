package ru.practicum.android.diploma.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.data.repository.ResourcesProviderRepositoryImpl
import ru.practicum.android.diploma.data.repository.areas.AreasRepositoryImpl
import ru.practicum.android.diploma.data.repository.favorite.FavoriteRepositoryImpl
import ru.practicum.android.diploma.data.repository.filteringsettings.FilterRepositoryImpl
import ru.practicum.android.diploma.data.repository.industrychoice.IndustryRepositoryImpl
import ru.practicum.android.diploma.data.repository.search.SearchRepositoryImpl
import ru.practicum.android.diploma.data.repository.sharing.SharingRepositoryImpl
import ru.practicum.android.diploma.data.repository.vacancy.VacancyRepositoryImpl
import ru.practicum.android.diploma.domain.areas.AreasRepository
import ru.practicum.android.diploma.domain.favorite.FavoriteRepository
import ru.practicum.android.diploma.domain.filteringsettings.FilterRepository
import ru.practicum.android.diploma.domain.industrychoice.IndustryRepository
import ru.practicum.android.diploma.domain.search.SearchRepository
import ru.practicum.android.diploma.domain.sharing.SharingRepository
import ru.practicum.android.diploma.domain.util.ResourcesProviderRepository
import ru.practicum.android.diploma.domain.vacancy.VacancyRepository

val repositoryModule = module {

    single<ResourcesProviderRepository> {
        ResourcesProviderRepositoryImpl(androidContext())
    }

    factory<SearchRepository> {
        SearchRepositoryImpl(get(), get())
    }

    factory<FavoriteRepository> {
        FavoriteRepositoryImpl(get(), get())
    }

    factory<VacancyRepository> {
        VacancyRepositoryImpl(get())
    }

    factory<SharingRepository> {
        SharingRepositoryImpl(get())
    }

    factory<FilterRepository> {
        FilterRepositoryImpl(get())
    }

    single<AreasRepository> {
        AreasRepositoryImpl(get())
    }

    factory<IndustryRepository> {
        IndustryRepositoryImpl(get())
    }
}
