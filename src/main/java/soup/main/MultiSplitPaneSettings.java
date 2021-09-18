package soup.main;

import com.alee.extended.split.MultiSplitState;
import com.alee.extended.split.MultiSplitViewState;

import java.util.ArrayList;
import java.util.List;

public class MultiSplitPaneSettings extends MultiSplitState {
    private List<MultiSplitViewState> splitViewStates;
    private MultiSplitViewState fileTree;
    public MultiSplitViewState getFileTree() {
        return fileTree;
    }
    private MultiSplitViewState center;
    public MultiSplitViewState getCenter() {
        return center;
    }
    private MultiSplitViewState editor;
    public MultiSplitViewState getEditor() {
        return editor;
    }
    public MultiSplitPaneSettings(){
        super();
        splitViewStates = new ArrayList<>();
        fileTree = new MultiSplitViewState();

        center = new MultiSplitViewState();
        editor = new MultiSplitViewState();
        //fileTree.setSize(100);
        splitViewStates.add(0,fileTree);
        splitViewStates.add(1,center);
        splitViewStates.add(2,editor);
        setStates(splitViewStates);
        //editor.setExpanded(false);
    }



    public void initList(){
    }


}
