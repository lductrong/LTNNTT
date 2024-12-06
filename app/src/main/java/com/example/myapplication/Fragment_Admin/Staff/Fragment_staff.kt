import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Data.Model.Model_staff
import com.example.myapplication.Fragment_Admin.Staff.Adapter_staff
import com.example.myapplication.Fragment_Admin.Staff.Staff_addstaff
import com.example.myapplication.Fragment_Admin.Staff.Staff_edit
import com.example.myapplication.R

class Fragment_staff : Fragment(), Adapter_staff.OnItemClickListener {
    private lateinit var dbhelper: DatabaseHelper
    private lateinit var button_newstaff: Button
    private lateinit var ryr_staff: RecyclerView
    private lateinit var adapter: Adapter_staff
    private lateinit var staffList: ArrayList<Model_staff>

    companion object {
        private const val REQUEST_EDIT_STAFF = 1
        private const val ADD = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_staff, container, false)
        dbhelper = DatabaseHelper(requireContext())
        button_newstaff = view.findViewById(R.id.button_newstaff)
        ryr_staff = view.findViewById(R.id.ryr_staff)

        button_newstaff.setOnClickListener {
            val intent = Intent(requireContext(), Staff_addstaff::class.java)
            startActivityForResult(intent, ADD)
        }

        getallstaff()

        return view
    }

    private fun getallstaff(){
        ryr_staff.layoutManager = LinearLayoutManager(requireContext())
        staffList = dbhelper.getAllStaff()
        adapter = Adapter_staff(staffList, this)
        ryr_staff.adapter = adapter
    }

    override fun onEditClick(position: Int) {
        val staff = staffList[position]
        val intent = Intent(requireContext(), Staff_edit::class.java).apply {
            putExtra(Staff_edit.EXTRA_STAFF, staff)
        }
        startActivityForResult(intent, REQUEST_EDIT_STAFF)
    }

    override fun onDeleteClick(position: Int) {
        val staff = staffList[position]
        dbhelper.deleteStaff(staff.staffid)
        staffList.removeAt(position)
        adapter.notifyItemRemoved(position)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_EDIT_STAFF && resultCode == Staff_edit.RESULT_UPDATED) {
            data?.let {
                val updatedStaff = it.getSerializableExtra(Staff_edit.EXTRA_STAFF) as Model_staff
                val position = staffList.indexOfFirst { staff -> staff.staffid == updatedStaff.staffid }
                if (position != -1) {
                    staffList[position] = updatedStaff
                    adapter.notifyItemChanged(position)
                }
            }
        }
        when(requestCode){
            ADD -> getallstaff()
        }
    }
}
