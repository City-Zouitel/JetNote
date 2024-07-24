package city.zouitel.screens.draw_screen

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import city.zouitel.note.ui.DataScreenModel
import city.zouitel.systemDesign.CommonRow
import city.zouitel.systemDesign.CommonConstants.ADD_ROUTE
import city.zouitel.systemDesign.CommonConstants.EDIT_ROUTE
import city.zouitel.systemDesign.CommonConstants.IMG_DIR
import city.zouitel.systemDesign.CommonConstants.JPEG
import city.zouitel.systemDesign.CommonIcons.CIRCLE_ICON
import city.zouitel.systemDesign.CommonIcons.DISK_ICON
import city.zouitel.systemDesign.CommonIcons.ERASER_BLACK_ICON
import city.zouitel.systemDesign.CommonIcons.REDO_ICON
import city.zouitel.systemDesign.CommonIcons.UNDO_ICON
import io.getstream.sketchbook.ColorPickerDialog
import io.getstream.sketchbook.PaintColorPalette
import io.getstream.sketchbook.Sketchbook
import io.getstream.sketchbook.rememberSketchbookController
import java.io.File

//@OptIn(ExperimentalMaterialApi::class)
//@SuppressLint("RememberReturnType")
//@Composable
//fun DrawingNote(
//    viewModule: DataScreenModel,
////    navController: NavController,
//    title: String,
//    description: String,
//    color: Int?,
//    priority: String,
//    uid: String,
//    textColor: Int,
//    audioDuration: Int?,
//    reminding: Long?
//) {
//    val context = LocalContext.current
//    val sheetState = rememberBottomSheetScaffoldState()
//    val controller = rememberSketchbookController()
//    var eraseState by remember { mutableStateOf(false) }
//
//    // TODO: move to viewmodel
//    val path = File(context.filesDir.path + "/" + IMG_DIR, "$uid.$JPEG")
//    val bitImg = BitmapFactory.decodeFile(path.absolutePath)
//
//    val bitmap = remember { mutableStateOf<Bitmap?>(bitImg) }
//    val imagesPath = context.filesDir.path + "/" + IMG_DIR
//
//    val colorPickerDialogState = remember { mutableStateOf(false) }
//    var currentBrushSize by remember { mutableFloatStateOf(0f) }
//
//    BottomSheetScaffold(
//        scaffoldState = sheetState,
//        sheetPeekHeight = 60.dp,
//        modifier = Modifier
//            .navigationBarsPadding(),
//        sheetContent = {
//            // icons row.
//            Row {
//                CommonRow(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .background(MaterialTheme.colors.surface)
//                        .height(60.dp)
//                ) {
//                    //
//                    Icon(
//                        painter = painterResource(id = UNDO_ICON),
//                        null,
//                        modifier = Modifier
//                            .size(20.dp)
//                            .clickable {
//                                controller.undo()
//                            },
//                        tint = if (controller.canUndo.value) MaterialTheme.colors.onSurface else MaterialTheme.colors.primary,
//                    )
//                    //
//                    Icon(
//                        painter = painterResource(id = REDO_ICON),
//                        contentDescription = null,
//                        tint = if (controller.canRedo.value) MaterialTheme.colors.onSurface else MaterialTheme.colors.primary,
//                        modifier = Modifier
//                            .size(20.dp)
//                            .clickable {
//                                controller.redo()
//                            }
//                    )
//                    //
//                    Canvas(
//                        modifier = Modifier
//                            .background(Color.Transparent)
//                            .padding(end = 25.dp)
//                            .size(30.dp)
//                            .clickable {
//                                colorPickerDialogState.value = true
//                            }
//                    ) {
//                        val canvasWidth = size.width
//                        val canvasHeight = size.height
//                        val radius = canvasHeight / 2
//                        val space = (canvasWidth - 6 * radius) / 4
//                        drawCircle(
//                            brush = Brush.sweepGradient(
//                                colors = gradientColors,
//                                center = Offset(canvasWidth - space - radius, canvasHeight / 2),
//                            ),
//                            radius = radius,
//                            center = Offset(canvasWidth - space - radius, canvasHeight / 2)
//                        )
//                    }
//                    //
//                    Icon(
//                        painter = painterResource(id = ERASER_BLACK_ICON),
//                        contentDescription = null,
//                        tint = if (eraseState) MaterialTheme.colors.onSurface else MaterialTheme.colors.primary,
//                        modifier = Modifier.clickable {
//                            eraseState = !eraseState
//                            controller.setEraseMode(eraseState)
//                        }
//                    )
//                    //
//                    Icon(
//                        painter = painterResource(id = DISK_ICON),
//                        contentDescription = null,
//                        tint = MaterialTheme.colors.onSurface,
//                        modifier = Modifier.clickable {
//
//                            bitmap.value = controller.getSketchbookBitmap().asAndroidBitmap()
//                            bitmap.value?.let {
//                                viewModule.saveImageLocally(it, imagesPath, "$uid.$JPEG")
//                            }
//                            bitImg?.let {
////                                navController.navigate(
////                                    route = EDIT_ROUTE + "/" +
////                                            uid + "/" +
////                                            title + "/" +
////                                            description + "/" +
////                                            color + "/" +
////                                            textColor + "/" +
////                                            priority + "/" +
////                                            audioDuration + "/" +
////                                            reminding
////                                )
////                            } ?: navController.navigate(
////                                route = ADD_ROUTE + "/" +
////                                        uid
////                            )
//                        })
//                }
//            }
//
//            Divider(modifier = Modifier.padding(10.dp))
//
//            // brush colors.
//            PaintColorPalette(controller = controller)
//
//            Divider(modifier = Modifier.padding(10.dp))
//
//            // brush widths.
//            LazyRow(
//                state = rememberLazyListState(),
//                horizontalArrangement = Arrangement.Center,
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.height(80.dp)
//            ) {
//                items(alphaDrawPaint){
//                    Spacer(modifier = Modifier.padding(5.dp))
//                    Box(
//                        contentAlignment = Alignment.Center,
//                        modifier = Modifier
//                            .size(45.dp)
//                            .clickable {
//                                controller.setPaintStrokeWidth(it.toFloat())
//                                currentBrushSize = it.toFloat()
//                            }
//                            .border(
//                                border = BorderStroke(
//                                    2.dp,
//                                    if (currentBrushSize == it.toFloat()) Color.Black else Color.Transparent
//                                ),
//                                shape = CircleShape
//                            )
//                    ){
//                        Icon(
//                            painter = painterResource(id = CIRCLE_ICON),
//                            contentDescription = null,
//                            modifier = Modifier.size(it.dp)
//
//
//                        )
//                    }
//                    Spacer(modifier = Modifier.padding(5.dp))
//                }
//            }
//        },
//        backgroundColor = MaterialTheme.colors.surface
//    ) {
//        controller.setPaintColor(Color.Cyan)
//        Sketchbook(
//            modifier = Modifier.fillMaxSize(),
//            controller = controller,
//            imageBitmap = bitmap.value?.asImageBitmap()
//        )
//        ColorPickerDialog(expanded = colorPickerDialogState, controller = controller)
//    }
//}
//
//private val alphaDrawPaint = (5..45).step(5).toList().toTypedArray()
//
//private val gradientColors = listOf(
//    Color.Red,
//    Color.Magenta,
//    Color.Blue,
//    Color.Cyan,
//    Color.Green,
//    Color.Yellow,
//    Color.Red
//)
