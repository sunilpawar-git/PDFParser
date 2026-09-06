package com.payslipmax.pdfparser.telemetry

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class InstallationIdManagerTest {
    private class FakeInstallationIdStorage(
        private var storedValue: String? = null,
    ) : InstallationIdStorage {
        override fun getInstallationId(): String? = storedValue

        override fun saveInstallationId(id: String) {
            storedValue = id
        }
    }

    @Test
    fun generatesValidFormatWhenNoStoredId() {
        val storage = FakeInstallationIdStorage(null)
        val manager = InstallationIdManager(storage)

        val id = manager.getOrCreateInstallationId()

        assertNotNull(id)
        assertTrue(
            id.matches(Regex("^PMX-[A-Z0-9]{6}$")),
            "Expected format PMX-XXXXXX with 6 uppercase alphanumeric chars, got: $id",
        )
    }

    @Test
    fun returnsPersistedIdWhenAlreadyStored() {
        val existingId = "PMX-B8C29D"
        val storage = FakeInstallationIdStorage(existingId)
        val manager = InstallationIdManager(storage)

        val id = manager.getOrCreateInstallationId()

        assertEquals(existingId, id)
    }

    @Test
    fun savesNewlyGeneratedIdToStorage() {
        val storage = FakeInstallationIdStorage(null)
        val manager = InstallationIdManager(storage)

        val generated = manager.getOrCreateInstallationId()
        val retrieved = storage.getInstallationId()

        assertEquals(generated, retrieved)
    }

    @Test
    fun repeatedCallsReturnIdenticalId() {
        val storage = FakeInstallationIdStorage(null)
        val manager = InstallationIdManager(storage)

        val first = manager.getOrCreateInstallationId()
        val second = manager.getOrCreateInstallationId()

        assertEquals(first, second)
    }
}
