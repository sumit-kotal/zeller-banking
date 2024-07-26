import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.zeller.terminalapp.R
import com.zeller.terminalapp.TestApplication
import com.zeller.terminalapp.data.model.Transactions
import com.zeller.terminalapp.data.model.UserAccount
import com.zeller.terminalapp.domain.MockUserRepository
import com.zeller.terminalapp.ui.adapter.TransactionsAdapter
import com.zeller.terminalapp.ui.view.MainActivity
import com.zeller.terminalapp.ui.viewmodel.MainViewModel
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.objectweb.asm.util.CheckClassAdapter.verify
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject

@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28], application = TestApplication::class)
class MainActivityTest {

    private lateinit var activity: MainActivity
    @Inject
    lateinit var mockUserRepository: MockUserRepository

    @Before
    fun setup() {
        // Initialize the activity
        activity = Robolectric.buildActivity(MainActivity::class.java).create().resume().get()
    }

    @Test
    fun `balance updates when user account changes`() {
        // Arrange
        val newBalance = 150.0f
        mockUserRepository.getUserAccount() = UserAccount(balance = newBalance)

        // Act
        activity.setupObservers()

        // Assert
        assertEquals(newBalance.toString(), activity.findViewById<TextView>(R.id.balance).text)
    }

    @Test
    fun `deposit button click updates balance`() {
        // Arrange
        val depositAmount = 50.0f
        val initialBalance = 100.0f
        val newBalance = initialBalance + depositAmount
        mockUserRepository.userAccountFlow.value = UserAccount(balance = initialBalance)
        activity.findViewById<EditText>(R.id.amountInput).setText(depositAmount.toString())

        // Act
        activity.findViewById<Button>(R.id.depositButton).performClick()

        // Assert
        verify(mockUserRepository).deposit(depositAmount)
        assertEquals(newBalance.toString(), activity.findViewById<TextView>(R.id.balance).text)
    }
}