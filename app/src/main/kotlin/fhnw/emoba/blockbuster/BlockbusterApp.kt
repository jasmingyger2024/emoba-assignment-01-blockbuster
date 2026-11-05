package fhnw.emoba.blockbuster

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import fhnw.emoba.AppBase
import fhnw.emoba.blockbuster.viewmodel.BlockbusterModel
import fhnw.emoba.blockbuster.ui.BlockbusterUI

object BlockbusterApp : AppBase<BlockbusterModel>() {

    @Composable
    override fun createViewModel(activity: ComponentActivity): BlockbusterModel {
          val vm = viewModel<BlockbusterModel>()
          // initial startup actions may come here
        
          return vm
    }


    @Composable
    override fun AppUi(viewModel: BlockbusterModel) {
        BlockbusterUI(viewModel)
    }

}
