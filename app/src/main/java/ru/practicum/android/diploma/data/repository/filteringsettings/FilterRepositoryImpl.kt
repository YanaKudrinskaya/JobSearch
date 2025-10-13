package ru.practicum.android.diploma.data.repository.filteringsettings

import ru.practicum.android.diploma.data.storage.Storage
import ru.practicum.android.diploma.domain.filteringsettings.FilterRepository
import ru.practicum.android.diploma.domain.filteringsettings.models.FilterSettings

class FilterRepositoryImpl(
    val storage: Storage
) : FilterRepository {
    override fun getFilterOptions(): FilterSettings {
        return storage.getFilterOptions()
    }

    override fun saveFilterOptions(filter: FilterSettings) {
        storage.saveFilterOptions(filter)
    }

    override fun getSavedIndustryId(): Int? {
        return storage.getFilterOptions().industryId
    }
}
