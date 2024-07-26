import com.zeller.terminalapp.data.model.Transactions
import com.zeller.terminalapp.data.model.UserAccount
import com.zeller.terminalapp.domain.UserRepository
import com.zeller.terminalapp.ui.viewmodel.MainViewModel
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@HiltAndroidTest
class MainViewModelTest {

    private lateinit var mainViewModel: MainViewModel
    private val userRepository: UserRepository = mock()

    @Before
    fun setup() {
        mainViewModel = MainViewModel(userRepository)
    }

    @Test
    fun `deposit updates balance and adds transaction`() = runBlocking {
        // Arrange
        val initialBalance = 100.0f
        val depositAmount = 50.0f
        val newBalance = initialBalance + depositAmount
        whenever(userRepository.getUserAccount()).thenReturn(UserAccount(balance = initialBalance))

        // Act
        mainViewModel.deposit(depositAmount)

        // Assert
        verify(userRepository).deposit(depositAmount)
        assertEquals(newBalance, mainViewModel.userAccount.first().balance)
    }

    @Test
    fun `withdraw updates balance and adds transaction`() = runBlocking {
        // Arrange
        val initialBalance = 100.0f
        val withdrawAmount = 30.0f
        val newBalance = initialBalance - withdrawAmount
        whenever(userRepository.getUserAccount()).thenReturn(UserAccount(balance = initialBalance))

        // Act
        mainViewModel.withdraw(withdrawAmount)

        // Assert
        verify(userRepository).withdraw(withdrawAmount)
        assertEquals(newBalance, mainViewModel.userAccount.first().balance)
    }

    @Test
    fun `withdraw fails when balance is insufficient`() = runBlocking {
        // Arrange
        val initialBalance = 100.0f
        val withdrawAmount = 150.0f
        whenever(userRepository.getUserAccount()).thenReturn(UserAccount(balance = initialBalance))

        // Act
        mainViewModel.withdraw(withdrawAmount)

        // Assert
        verify(userRepository, never()).withdraw(withdrawAmount)
        assertEquals("Not enough balance", mainViewModel.transactionStatus.first())
    }
}