package dev.esteki.ibank.core.data.settings

import dev.esteki.ibank.core.data.db.mapper.toDomain
import dev.esteki.ibank.core.data.user.UserProfileEntity
import dev.esteki.ibank.core.domain.common.AppError
import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.settings.model.Settings
import dev.esteki.ibank.core.domain.settings.model.SettingsItem
import dev.esteki.ibank.core.domain.settings.model.SettingsItemType
import dev.esteki.ibank.core.domain.settings.model.SettingsSection
import dev.esteki.ibank.core.domain.settings.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.serialization.json.Json

class SettingsRepositoryImpl(
    private val localDataSource: SettingsLocalDataSource,
) : SettingsRepository {

    private val json = Json { ignoreUnknownKeys = true }

    override fun getSettings(): Flow<Result<Settings>> =
        combine(
            localDataSource.observeProfile(),
            localDataSource.observeSettings(),
        ) { profileEntity: UserProfileEntity?, settingsEntity: SettingsEntity? ->
            if (profileEntity != null && settingsEntity != null) {
                val sectionsDto =
                    json.decodeFromString<List<SettingsSectionDto>>(settingsEntity.sectionsJson)
                val sections = sectionsDto.map { sectionDto ->
                    SettingsSection(
                        id = sectionDto.id,
                        title = sectionDto.title,
                        items = sectionDto.items.map { itemDto ->
                            SettingsItem(
                                id = itemDto.id,
                                label = itemDto.label,
                                iconRes = itemDto.iconRes,
                                type = SettingsItemType.valueOf(itemDto.type),
                                subtitle = itemDto.subtitle,
                                isChecked = itemDto.isChecked,
                            )
                        },
                    )
                }
                Result.Success(
                    Settings(
                        profile = profileEntity.toDomain(),
                        sections = sections,
                    ),
                )
            } else {
                Result.Failure(AppError.NotFound)
            }
        }
}
