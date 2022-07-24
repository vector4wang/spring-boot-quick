package com.quick.flowable;

import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.*;
import org.flowable.bpmn.model.Process;
import org.flowable.image.ProcessDiagramGenerator;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.*;

/**
 *生成图像
 *
 * @author liuxz
 */
@Slf4j
public class CustomProcessDiagramGenerator implements ProcessDiagramGenerator {


    protected Map<Class<? extends BaseElement>, ActivityDrawInstruction> activityDrawInstructions = new HashMap<Class<? extends BaseElement>, ActivityDrawInstruction>();
    protected Map<Class<? extends BaseElement>, ArtifactDrawInstruction> artifactDrawInstructions = new HashMap<Class<? extends BaseElement>, ArtifactDrawInstruction>();

    public CustomProcessDiagramGenerator() {
        this(1.0);
    }

    // The instructions on how to draw a certain construct is
    // created statically and stored in a map for performance.
    public CustomProcessDiagramGenerator(final double scaleFactor) {
        // start event
        activityDrawInstructions.put(StartEvent.class, new ActivityDrawInstruction() {

            @Override
            public void draw(CustomProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode) {
                GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
                StartEvent startEvent = (StartEvent) flowNode;
                if (startEvent.getEventDefinitions() != null && !startEvent.getEventDefinitions().isEmpty()) {
                    EventDefinition eventDefinition = startEvent.getEventDefinitions().get(0);
                    if (eventDefinition instanceof TimerEventDefinition) {
                        processDiagramCanvas.drawTimerStartEvent(graphicInfo, scaleFactor);
                    } else if (eventDefinition instanceof ErrorEventDefinition) {
                        processDiagramCanvas.drawErrorStartEvent(graphicInfo, scaleFactor);
                    } else if (eventDefinition instanceof SignalEventDefinition) {
                        processDiagramCanvas.drawSignalStartEvent(graphicInfo, scaleFactor);
                    } else if (eventDefinition instanceof MessageEventDefinition) {
                        processDiagramCanvas.drawMessageStartEvent(graphicInfo, scaleFactor);
                    } else {
                        processDiagramCanvas.drawNoneStartEvent(graphicInfo);
                    }
                } else {
                    processDiagramCanvas.drawNoneStartEvent(graphicInfo);
                }
            }
        });

        // signal catch
        activityDrawInstructions.put(IntermediateCatchEvent.class, new ActivityDrawInstruction() {

            @Override
            public void draw(CustomProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode) {
                GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
                IntermediateCatchEvent intermediateCatchEvent = (IntermediateCatchEvent) flowNode;
                if (intermediateCatchEvent.getEventDefinitions() != null && !intermediateCatchEvent
                        .getEventDefinitions().isEmpty()) {
                    if (intermediateCatchEvent.getEventDefinitions().get(0) instanceof SignalEventDefinition) {
                        processDiagramCanvas.drawCatchingSignalEvent(flowNode.getName(), graphicInfo, true,
                                scaleFactor);
                    } else if (intermediateCatchEvent.getEventDefinitions().get(0) instanceof TimerEventDefinition) {
                        processDiagramCanvas.drawCatchingTimerEvent(flowNode.getName(), graphicInfo, true, scaleFactor);
                    } else if (intermediateCatchEvent.getEventDefinitions().get(0) instanceof MessageEventDefinition) {
                        processDiagramCanvas.drawCatchingMessageEvent(flowNode.getName(), graphicInfo, true,
                                scaleFactor);
                    }
                }
            }
        });

        // signal throw
        activityDrawInstructions.put(ThrowEvent.class, new ActivityDrawInstruction() {

            @Override
            public void draw(CustomProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode) {
                GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
                ThrowEvent throwEvent = (ThrowEvent) flowNode;
                if (throwEvent.getEventDefinitions() != null && !throwEvent.getEventDefinitions().isEmpty()) {
                    if (throwEvent.getEventDefinitions().get(0) instanceof SignalEventDefinition) {
                        processDiagramCanvas.drawThrowingSignalEvent(graphicInfo, scaleFactor);
                    } else if (throwEvent.getEventDefinitions().get(0) instanceof CompensateEventDefinition) {
                        processDiagramCanvas.drawThrowingCompensateEvent(graphicInfo, scaleFactor);
                    } else {
                        processDiagramCanvas.drawThrowingNoneEvent(graphicInfo, scaleFactor);
                    }
                } else {
                    processDiagramCanvas.drawThrowingNoneEvent(graphicInfo, scaleFactor);
                }
            }
        });

        // end event
        activityDrawInstructions.put(EndEvent.class, new ActivityDrawInstruction() {

            @Override
            public void draw(CustomProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode) {
                GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
                EndEvent endEvent = (EndEvent) flowNode;
                if (endEvent.getEventDefinitions() != null && !endEvent.getEventDefinitions().isEmpty()) {
                    if (endEvent.getEventDefinitions().get(0) instanceof ErrorEventDefinition) {
                        processDiagramCanvas.drawErrorEndEvent(flowNode.getName(), graphicInfo, scaleFactor);
                    } else {
                        processDiagramCanvas.drawNoneEndEvent(graphicInfo, scaleFactor);
                    }
                } else {
                    processDiagramCanvas.drawNoneEndEvent(graphicInfo, scaleFactor);
                }
            }
        });

        // task
        activityDrawInstructions.put(Task.class, new ActivityDrawInstruction() {

            @Override
            public void draw(CustomProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode) {
                GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
                processDiagramCanvas.drawTask(flowNode.getName(), graphicInfo);
            }
        });

        // user task
        activityDrawInstructions.put(UserTask.class, new ActivityDrawInstruction() {

            @Override
            public void draw(CustomProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode) {
                GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
                processDiagramCanvas.drawUserTask(flowNode.getName(), graphicInfo, scaleFactor);
            }
        });

        // script task
        activityDrawInstructions.put(ScriptTask.class, new ActivityDrawInstruction() {

            @Override
            public void draw(CustomProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode) {
                GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
                processDiagramCanvas.drawScriptTask(flowNode.getName(), graphicInfo, scaleFactor);
            }
        });

        // service task
        activityDrawInstructions.put(ServiceTask.class, new ActivityDrawInstruction() {

            @Override
            public void draw(CustomProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode) {
                GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
                ServiceTask serviceTask = (ServiceTask) flowNode;
                if ("camel".equalsIgnoreCase(serviceTask.getType())) {
                    processDiagramCanvas.drawCamelTask(serviceTask.getName(), graphicInfo, scaleFactor);
                } else if ("mule".equalsIgnoreCase(serviceTask.getType())) {
                    processDiagramCanvas.drawMuleTask(serviceTask.getName(), graphicInfo, scaleFactor);
                } else {
                    processDiagramCanvas.drawServiceTask(serviceTask.getName(), graphicInfo, scaleFactor);
                }
            }
        });

        // receive task
        activityDrawInstructions.put(ReceiveTask.class, new ActivityDrawInstruction() {

            @Override
            public void draw(CustomProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode) {
                GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
                processDiagramCanvas.drawReceiveTask(flowNode.getName(), graphicInfo, scaleFactor);
            }
        });

        // send task
        activityDrawInstructions.put(SendTask.class, new ActivityDrawInstruction() {

            @Override
            public void draw(CustomProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode) {
                GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
                processDiagramCanvas.drawSendTask(flowNode.getName(), graphicInfo, scaleFactor);
            }
        });

        // manual task
        activityDrawInstructions.put(ManualTask.class, new ActivityDrawInstruction() {

            @Override
            public void draw(CustomProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode) {
                GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
                processDiagramCanvas.drawManualTask(flowNode.getName(), graphicInfo, scaleFactor);
            }
        });

        // businessRuleTask task
        activityDrawInstructions.put(BusinessRuleTask.class, new ActivityDrawInstruction() {

            @Override
            public void draw(CustomProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode) {
                GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
                processDiagramCanvas.drawBusinessRuleTask(flowNode.getName(), graphicInfo, scaleFactor);
            }
        });

        // exclusive gateway
        activityDrawInstructions.put(ExclusiveGateway.class, new ActivityDrawInstruction() {

            @Override
            public void draw(CustomProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode) {
                GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
                processDiagramCanvas.drawExclusiveGateway(graphicInfo, scaleFactor);
            }
        });

        // inclusive gateway
        activityDrawInstructions.put(InclusiveGateway.class, new ActivityDrawInstruction() {

            @Override
            public void draw(CustomProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode) {
                GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
                processDiagramCanvas.drawInclusiveGateway(graphicInfo, scaleFactor);
            }
        });

        // parallel gateway
        activityDrawInstructions.put(ParallelGateway.class, new ActivityDrawInstruction() {

            @Override
            public void draw(CustomProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode) {
                GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
                processDiagramCanvas.drawParallelGateway(graphicInfo, scaleFactor);
            }
        });

        // event based gateway
        activityDrawInstructions.put(EventGateway.class, new ActivityDrawInstruction() {

            @Override
            public void draw(CustomProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode) {
                GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
                processDiagramCanvas.drawEventBasedGateway(graphicInfo, scaleFactor);
            }
        });

        // Boundary timer
        activityDrawInstructions.put(BoundaryEvent.class, new ActivityDrawInstruction() {

            @Override
            public void draw(CustomProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode) {
                GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
                BoundaryEvent boundaryEvent = (BoundaryEvent) flowNode;
                if (boundaryEvent.getEventDefinitions() != null && !boundaryEvent.getEventDefinitions().isEmpty()) {
                    if (boundaryEvent.getEventDefinitions().get(0) instanceof TimerEventDefinition) {

                        processDiagramCanvas.drawCatchingTimerEvent(flowNode.getName(), graphicInfo, boundaryEvent
                                .isCancelActivity(), scaleFactor);

                    } else if (boundaryEvent.getEventDefinitions().get(0) instanceof ErrorEventDefinition) {

                        processDiagramCanvas.drawCatchingErrorEvent(graphicInfo, boundaryEvent.isCancelActivity(),
                                scaleFactor);

                    } else if (boundaryEvent.getEventDefinitions().get(0) instanceof SignalEventDefinition) {
                        processDiagramCanvas.drawCatchingSignalEvent(flowNode.getName(), graphicInfo, boundaryEvent
                                .isCancelActivity(), scaleFactor);

                    } else if (boundaryEvent.getEventDefinitions().get(0) instanceof MessageEventDefinition) {
                        processDiagramCanvas.drawCatchingMessageEvent(flowNode.getName(), graphicInfo, boundaryEvent
                                .isCancelActivity(), scaleFactor);

                    } else if (boundaryEvent.getEventDefinitions().get(0) instanceof CompensateEventDefinition) {
                        processDiagramCanvas.drawCatchingCompensateEvent(graphicInfo, boundaryEvent.isCancelActivity(),
                                scaleFactor);
                    }
                }

            }
        });

        // subprocess
        activityDrawInstructions.put(SubProcess.class, new ActivityDrawInstruction() {

            @Override
            public void draw(CustomProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode) {
                GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
                if (graphicInfo.getExpanded() != null && !graphicInfo.getExpanded()) {
                    processDiagramCanvas.drawCollapsedSubProcess(flowNode.getName(), graphicInfo, false);
                } else {
                    processDiagramCanvas.drawExpandedSubProcess(flowNode.getName(), graphicInfo, false, scaleFactor);
                }
            }
        });

        // Event subprocess
        activityDrawInstructions.put(EventSubProcess.class, new ActivityDrawInstruction() {

            @Override
            public void draw(CustomProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode) {
                GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
                if (graphicInfo.getExpanded() != null && !graphicInfo.getExpanded()) {
                    processDiagramCanvas.drawCollapsedSubProcess(flowNode.getName(), graphicInfo, true);
                } else {
                    processDiagramCanvas.drawExpandedSubProcess(flowNode.getName(), graphicInfo, true, scaleFactor);
                }
            }
        });

        // call activity
        activityDrawInstructions.put(CallActivity.class, new ActivityDrawInstruction() {

            @Override
            public void draw(CustomProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode) {
                GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
                processDiagramCanvas.drawCollapsedCallActivity(flowNode.getName(), graphicInfo);
            }
        });

        // text annotation
        artifactDrawInstructions.put(TextAnnotation.class, new ArtifactDrawInstruction() {

            @Override
            public void draw(CustomProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, Artifact artifact) {
                GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(artifact.getId());
                TextAnnotation textAnnotation = (TextAnnotation) artifact;
                processDiagramCanvas.drawTextAnnotation(textAnnotation.getText(), graphicInfo);
            }
        });

        // association
        artifactDrawInstructions.put(Association.class, new ArtifactDrawInstruction() {

            @Override
            public void draw(CustomProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, Artifact artifact) {
                Association association = (Association) artifact;
                String sourceRef = association.getSourceRef();
                String targetRef = association.getTargetRef();

                // source and target can be instance of FlowElement or Artifact
                BaseElement sourceElement = bpmnModel.getFlowElement(sourceRef);
                BaseElement targetElement = bpmnModel.getFlowElement(targetRef);
                if (sourceElement == null) {
                    sourceElement = bpmnModel.getArtifact(sourceRef);
                }
                if (targetElement == null) {
                    targetElement = bpmnModel.getArtifact(targetRef);
                }
                List<GraphicInfo> graphicInfoList = bpmnModel.getFlowLocationGraphicInfo(artifact.getId());
                graphicInfoList = connectionPerfectionizer(processDiagramCanvas, bpmnModel, sourceElement,
                        targetElement, graphicInfoList);
                int[] xPoints = new int[graphicInfoList.size()];
                int[] yPoints = new int[graphicInfoList.size()];
                for (int i = 1; i < graphicInfoList.size(); i++) {
                    GraphicInfo graphicInfo = graphicInfoList.get(i);
                    GraphicInfo previousGraphicInfo = graphicInfoList.get(i - 1);

                    if (i == 1) {
                        xPoints[0] = (int) previousGraphicInfo.getX();
                        yPoints[0] = (int) previousGraphicInfo.getY();
                    }
                    xPoints[i] = (int) graphicInfo.getX();
                    yPoints[i] = (int) graphicInfo.getY();
                }

                AssociationDirection associationDirection = association.getAssociationDirection();
                processDiagramCanvas.drawAssociation(xPoints, yPoints, associationDirection, false, scaleFactor);
            }
        });
    }

    public InputStream generateDiagram(BpmnModel bpmnModel, String imageType, List<String> highLightedActivities,
                                       List<String> highLightedFlows, String activityFontName, String labelFontName, ClassLoader customClassLoader,
                                       double scaleFactor, boolean drawSequenceFlowNameWithNoLabelDI) {

        return generateProcessDiagram(bpmnModel, imageType, highLightedActivities, new ArrayList<String>(),
                highLightedFlows, activityFontName, labelFontName, customClassLoader, scaleFactor, drawSequenceFlowNameWithNoLabelDI).generateImage(
                imageType);
    }


    @Override
    public InputStream generateDiagram(BpmnModel bpmnModel, String imageType, List<String> highLightedActivities,
                                       List<String> highLightedFlows, boolean drawSequenceFlowNameWithNoLabelDI) {
        return generateDiagram(bpmnModel, imageType, highLightedActivities, highLightedFlows, null, null, null, 1.0, drawSequenceFlowNameWithNoLabelDI);
    }

    @Override
    public InputStream generateDiagram(BpmnModel bpmnModel, String imageType, List<String> highLightedActivities,
                                       List<String> highLightedFlows, double scaleFactor, boolean drawSequenceFlowNameWithNoLabelDI) {
        return generateDiagram(bpmnModel, imageType, highLightedActivities, highLightedFlows, null, null, null,
                scaleFactor, drawSequenceFlowNameWithNoLabelDI);
    }

    @Override
    public InputStream generateDiagram(BpmnModel bpmnModel, String imageType, List<String> highLightedActivities, boolean drawSequenceFlowNameWithNoLabelDI) {
        return generateDiagram(bpmnModel, imageType, highLightedActivities, Collections.<String>emptyList(), drawSequenceFlowNameWithNoLabelDI);
    }


    @Override
    public InputStream generateDiagram(BpmnModel bpmnModel, String imageType, List<String> highLightedActivities,
                                       double scaleFactor, boolean drawSequenceFlowNameWithNoLabelDI) {
        return generateDiagram(bpmnModel, imageType, highLightedActivities, Collections.<String>emptyList(),
                scaleFactor, drawSequenceFlowNameWithNoLabelDI);
    }

    public InputStream generateDiagram(BpmnModel bpmnModel, String imageType, String activityFontName,
                                       String labelFontName, ClassLoader customClassLoader, boolean drawSequenceFlowNameWithNoLabelDI) {
        return generateDiagram(bpmnModel, imageType, Collections.<String>emptyList(), Collections.<String>emptyList(),
                activityFontName, labelFontName, customClassLoader, 1.0, drawSequenceFlowNameWithNoLabelDI);
    }

    public InputStream generateDiagram(BpmnModel bpmnModel, String imageType, String activityFontName,
                                       String labelFontName, ClassLoader customClassLoader, double scaleFactor, boolean drawSequenceFlowNameWithNoLabelDI) {

        return generateDiagram(bpmnModel, imageType, Collections.<String>emptyList(), Collections.<String>emptyList(),
                activityFontName, labelFontName, customClassLoader, scaleFactor, drawSequenceFlowNameWithNoLabelDI);
    }

    public InputStream generatePngDiagram(BpmnModel bpmnModel) {

        return generatePngDiagram(bpmnModel, 1.0, true);
    }

    @Override
    public InputStream generatePngDiagram(BpmnModel bpmnModel, double scaleFactor, boolean drawSequenceFlowNameWithNoLabelDI) {
        return generateDiagram(bpmnModel, "png", Collections.<String>emptyList(), Collections.<String>emptyList(),
                scaleFactor, drawSequenceFlowNameWithNoLabelDI);
    }

    @Override
    public InputStream generateJpgDiagram(BpmnModel bpmnModel) {
        return generateDiagram(bpmnModel, "jpg", Collections.<String>emptyList(), Collections.<String>emptyList(), true);

    }

    @Override
    public InputStream generateJpgDiagram(BpmnModel bpmnModel, double scaleFactor, boolean drawSequenceFlowNameWithNoLabelDI) {
        return generateDiagram(bpmnModel, "jpg", Collections.<String>emptyList(), Collections.<String>emptyList(), drawSequenceFlowNameWithNoLabelDI);
    }


    public BufferedImage generateImage(BpmnModel bpmnModel, String imageType, List<String> highLightedActivities,
                                       List<String> highLightedFlows, String activityFontName, String labelFontName, ClassLoader customClassLoader,
                                       double scaleFactor, boolean drawSequenceFlowNameWithNoLabelDI) {

        return generateProcessDiagram(bpmnModel, imageType, highLightedActivities, new ArrayList<String>(),
                highLightedFlows, activityFontName, labelFontName, customClassLoader, scaleFactor, drawSequenceFlowNameWithNoLabelDI)
                .generateBufferedImage(imageType);
    }

    public BufferedImage generateImage(BpmnModel bpmnModel, String imageType, List<String> highLightedActivities,
                                       List<String> highLightedFlows, double scaleFactor, boolean drawSequenceFlowNameWithNoLabelDI) {

        return generateImage(bpmnModel, imageType, highLightedActivities, highLightedFlows, null, null, null,
                scaleFactor, drawSequenceFlowNameWithNoLabelDI);
    }


    protected CustomProcessDiagramCanvas generateProcessDiagram(BpmnModel bpmnModel, String imageType,
                                                                List<String> highLightedActivities, List<String> runningActivitiIdList, List<String> highLightedFlows,
                                                                String activityFontName, String labelFontName, ClassLoader customClassLoader, double scaleFactor, boolean drawSequenceFlowNameWithNoLabelDI) {

        CustomProcessDiagramCanvas processDiagramCanvas = initProcessDiagramCanvas(bpmnModel, imageType,
                activityFontName, labelFontName, customClassLoader);

        // Draw pool shape, if process is participant in collaboration
        for (Pool pool : bpmnModel.getPools()) {
            GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(pool.getId());
            processDiagramCanvas.drawPoolOrLane(pool.getName(), graphicInfo);
        }

        // Draw lanes
        for (Process process : bpmnModel.getProcesses()) {
            for (Lane lane : process.getLanes()) {
                GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(lane.getId());
                processDiagramCanvas.drawPoolOrLane(lane.getName(), graphicInfo);
            }
        }

        // Draw activities and their sequence-flows
        /**
         * 绘制流程图上的所有节点和流程线，对高亮显示的节点和流程线进行特殊处理
         */
        for (FlowNode flowNode : bpmnModel.getProcesses().get(0).findFlowElementsOfType(FlowNode.class)) {
            drawActivity(processDiagramCanvas, bpmnModel, flowNode, highLightedActivities, runningActivitiIdList,
                    highLightedFlows, scaleFactor);
        }

        // Draw artifacts
        for (Process process : bpmnModel.getProcesses()) {
            for (Artifact artifact : process.getArtifacts()) {
                drawArtifact(processDiagramCanvas, bpmnModel, artifact);
            }
        }

        return processDiagramCanvas;
    }

    /**
     * Desc: 绘制流程图上的所有节点和流程线，对高亮显示的节点和流程线进行特殊处理
     *
     * @param processDiagramCanvas
     * @param bpmnModel
     * @param flowNode
     * @param highLightedActivities
     * @param highLightedFlows
     * @param scaleFactor
     * @author Fuxs
     */
    protected void drawActivity(CustomProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode,
                                List<String> highLightedActivities, List<String> runningActivitiIdList, List<String> highLightedFlows,
                                double scaleFactor) {
        ActivityDrawInstruction drawInstruction = activityDrawInstructions.get(flowNode.getClass());
        if (drawInstruction != null) {
            drawInstruction.draw(processDiagramCanvas, bpmnModel, flowNode);
            // Gather info on the multi instance marker
            boolean multiInstanceSequential = false, multiInstanceParallel = false, collapsed = false;
            if (flowNode instanceof Activity) {
                Activity activity = (Activity) flowNode;
                MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics = activity.getLoopCharacteristics();
                if (multiInstanceLoopCharacteristics != null) {
                    multiInstanceSequential = multiInstanceLoopCharacteristics.isSequential();
                    multiInstanceParallel = !multiInstanceSequential;
                }
            }

            // Gather info on the collapsed marker
            GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
            if (flowNode instanceof SubProcess) {
                collapsed = graphicInfo.getExpanded() != null && !graphicInfo.getExpanded();
            } else if (flowNode instanceof CallActivity) {
                collapsed = true;
            }

            if (scaleFactor == 1.0) {
                // Actually draw the markers
                processDiagramCanvas.drawActivityMarkers((int) graphicInfo.getX(), (int) graphicInfo.getY(),
                        (int) graphicInfo.getWidth(), (int) graphicInfo.getHeight(), multiInstanceSequential,
                        multiInstanceParallel, collapsed);
            }
            // 绘制需要突出强调的活动
            if (highLightedActivities.contains(flowNode.getId())) {
                //如果节点为当前正在处理中的节点，则红色高亮显示
                if (runningActivitiIdList.contains(flowNode.getId())) {
                    log.debug("[绘制]-当前正在处理中的节点-红色高亮显示节点[{}-{}]", flowNode.getId(), flowNode.getName());
                    drawRunningActivitiHighLight(processDiagramCanvas, bpmnModel.getGraphicInfo(flowNode.getId()));
                } else {
                    log.debug("[绘制]-高亮显示节点[{}-{}]", flowNode.getId(), flowNode.getName());
                    drawHighLight(processDiagramCanvas, bpmnModel.getGraphicInfo(flowNode.getId()));
                }
            }
        }
        // 绘制当前节点的流程线
        for (SequenceFlow sequenceFlow : flowNode.getOutgoingFlows()) {
            boolean highLighted = (highLightedFlows.contains(sequenceFlow.getId()));
            String defaultFlow = null;
            if (flowNode instanceof Activity) {
                defaultFlow = ((Activity) flowNode).getDefaultFlow();
            } else if (flowNode instanceof Gateway) {
                defaultFlow = ((Gateway) flowNode).getDefaultFlow();
            }

            boolean isDefault = false;
            if (defaultFlow != null && defaultFlow.equalsIgnoreCase(sequenceFlow.getId())) {
                isDefault = true;
            }
            boolean drawConditionalIndicator = sequenceFlow.getConditionExpression() != null && !(flowNode instanceof Gateway);

            String sourceRef = sequenceFlow.getSourceRef();
            String targetRef = sequenceFlow.getTargetRef();
            FlowElement sourceElement = bpmnModel.getFlowElement(sourceRef);
            FlowElement targetElement = bpmnModel.getFlowElement(targetRef);
            List<GraphicInfo> graphicInfoList = bpmnModel.getFlowLocationGraphicInfo(sequenceFlow.getId());
            if (graphicInfoList != null && graphicInfoList.size() > 0) {
                graphicInfoList = connectionPerfectionizer(processDiagramCanvas, bpmnModel, sourceElement,
                        targetElement, graphicInfoList);
                int[] xPoints = new int[graphicInfoList.size()];
                int[] yPoints = new int[graphicInfoList.size()];
                for (int i = 1; i < graphicInfoList.size(); i++) {
                    GraphicInfo graphicInfo = graphicInfoList.get(i);
                    GraphicInfo previousGraphicInfo = graphicInfoList.get(i - 1);
                    if (i == 1) {
                        xPoints[0] = (int) previousGraphicInfo.getX();
                        yPoints[0] = (int) previousGraphicInfo.getY();
                    }
                    xPoints[i] = (int) graphicInfo.getX();
                    yPoints[i] = (int) graphicInfo.getY();

                }

                processDiagramCanvas.drawSequenceflow(xPoints, yPoints, drawConditionalIndicator, isDefault,
                        highLighted, scaleFactor);
                //绘制流程线名称
                GraphicInfo labelGraphicInfo = bpmnModel.getLabelGraphicInfo(sequenceFlow.getId());
//				if (labelGraphicInfo != null) {
                GraphicInfo lineCenter = getLineCenter(graphicInfoList);
                processDiagramCanvas.drawLabel(sequenceFlow.getName(), lineCenter, true);
//				}
            }
        }

        // Nested elements
        if (flowNode instanceof FlowElementsContainer) {
            for (FlowElement nestedFlowElement : ((FlowElementsContainer) flowNode).getFlowElements()) {
                if (nestedFlowElement instanceof FlowNode) {
                    drawActivity(processDiagramCanvas, bpmnModel, (FlowNode) nestedFlowElement, highLightedActivities,
                            runningActivitiIdList, highLightedFlows, scaleFactor);
                }
            }
        }
    }

    /**
     * This method makes coordinates of connection flow better.
     *
     * @param processDiagramCanvas
     * @param bpmnModel
     * @param sourceElement
     * @param targetElement
     * @param graphicInfoList
     * @return
     */
    protected static List<GraphicInfo> connectionPerfectionizer(CustomProcessDiagramCanvas processDiagramCanvas,
                                                                BpmnModel bpmnModel, BaseElement sourceElement, BaseElement targetElement,
                                                                List<GraphicInfo> graphicInfoList) {
        GraphicInfo sourceGraphicInfo = bpmnModel.getGraphicInfo(sourceElement.getId());
        GraphicInfo targetGraphicInfo = bpmnModel.getGraphicInfo(targetElement.getId());

        CustomProcessDiagramCanvas.SHAPE_TYPE sourceShapeType = getShapeType(sourceElement);
        CustomProcessDiagramCanvas.SHAPE_TYPE targetShapeType = getShapeType(targetElement);

        return processDiagramCanvas.connectionPerfectionizer(sourceShapeType, targetShapeType, sourceGraphicInfo,
                targetGraphicInfo, graphicInfoList);
    }

    /**
     * This method returns shape type of base element.<br>
     * Each element can be presented as rectangle, rhombus, or ellipse.
     *
     * @param baseElement
     * @return CustomProcessDiagramCanvas.SHAPE_TYPE
     */
    protected static CustomProcessDiagramCanvas.SHAPE_TYPE getShapeType(BaseElement baseElement) {
        if (baseElement instanceof Task || baseElement instanceof Activity || baseElement instanceof TextAnnotation) {
            return CustomProcessDiagramCanvas.SHAPE_TYPE.Rectangle;
        } else if (baseElement instanceof Gateway) {
            return CustomProcessDiagramCanvas.SHAPE_TYPE.Rhombus;
        } else if (baseElement instanceof Event) {
            return CustomProcessDiagramCanvas.SHAPE_TYPE.Ellipse;
        } else {
            // unknown source element, just do not correct coordinates
        }
        return null;
    }

    protected static GraphicInfo getLineCenter(List<GraphicInfo> graphicInfoList) {
        GraphicInfo gi = new GraphicInfo();

        int[] xPoints = new int[graphicInfoList.size()];
        int[] yPoints = new int[graphicInfoList.size()];

        double length = 0;
        double[] lengths = new double[graphicInfoList.size()];
        lengths[0] = 0;
        double m;
        for (int i = 1; i < graphicInfoList.size(); i++) {
            GraphicInfo graphicInfo = graphicInfoList.get(i);
            GraphicInfo previousGraphicInfo = graphicInfoList.get(i - 1);

            if (i == 1) {
                xPoints[0] = (int) previousGraphicInfo.getX();
                yPoints[0] = (int) previousGraphicInfo.getY();
            }
            xPoints[i] = (int) graphicInfo.getX();
            yPoints[i] = (int) graphicInfo.getY();

            length += Math.sqrt(Math.pow((int) graphicInfo.getX() - (int) previousGraphicInfo.getX(), 2) + Math.pow(
                    (int) graphicInfo.getY() - (int) previousGraphicInfo.getY(), 2));
            lengths[i] = length;
        }
        m = length / 2;
        int p1 = 0, p2 = 1;
        for (int i = 1; i < lengths.length; i++) {
            double len = lengths[i];
            p1 = i - 1;
            p2 = i;
            if (len > m) {
                break;
            }
        }

        GraphicInfo graphicInfo1 = graphicInfoList.get(p1);
        GraphicInfo graphicInfo2 = graphicInfoList.get(p2);

        double AB = (int) graphicInfo2.getX() - (int) graphicInfo1.getX();
        double OA = (int) graphicInfo2.getY() - (int) graphicInfo1.getY();
        double OB = lengths[p2] - lengths[p1];
        double ob = m - lengths[p1];
        double ab = AB * ob / OB;
        double oa = OA * ob / OB;

        double mx = graphicInfo1.getX() + ab;
        double my = graphicInfo1.getY() + oa;

        gi.setX(mx);
        gi.setY(my);
        return gi;
    }

    protected void drawArtifact(CustomProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel,
                                Artifact artifact) {

        ArtifactDrawInstruction drawInstruction = artifactDrawInstructions.get(artifact.getClass());
        if (drawInstruction != null) {
            drawInstruction.draw(processDiagramCanvas, bpmnModel, artifact);
        }
    }

    private static void drawHighLight(CustomProcessDiagramCanvas processDiagramCanvas, GraphicInfo graphicInfo) {
        processDiagramCanvas.drawHighLight((int) graphicInfo.getX(), (int) graphicInfo.getY(), (int) graphicInfo
                .getWidth(), (int) graphicInfo.getHeight());

    }

    /**
     * Desc:绘制正在执行中的节点红色高亮显示
     *
     * @param processDiagramCanvas
     * @param graphicInfo
     * @author Fuxs
     */
    private static void drawRunningActivitiHighLight(CustomProcessDiagramCanvas processDiagramCanvas, GraphicInfo graphicInfo) {
        processDiagramCanvas.drawRunningActivitiHighLight((int) graphicInfo.getX(), (int) graphicInfo.getY(), (int) graphicInfo
                .getWidth(), (int) graphicInfo.getHeight());

    }

    protected static CustomProcessDiagramCanvas initProcessDiagramCanvas(BpmnModel bpmnModel, String imageType,
                                                                         String activityFontName, String labelFontName, ClassLoader customClassLoader) {
        // 我们需要计算最大值以了解图像的整体大小
        double minX = Double.MAX_VALUE;
        double maxX = 0;
        double minY = Double.MAX_VALUE;
        double maxY = 0;

        for (Pool pool : bpmnModel.getPools()) {
            GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(pool.getId());
            minX = graphicInfo.getX();
            maxX = graphicInfo.getX() + graphicInfo.getWidth();
            minY = graphicInfo.getY();
            maxY = graphicInfo.getY() + graphicInfo.getHeight();
        }

        List<FlowNode> flowNodes = gatherAllFlowNodes(bpmnModel);
        for (FlowNode flowNode : flowNodes) {

            GraphicInfo flowNodeGraphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());

            // 宽度
            if (flowNodeGraphicInfo.getX() + flowNodeGraphicInfo.getWidth() > maxX) {
                maxX = flowNodeGraphicInfo.getX() + flowNodeGraphicInfo.getWidth();
            }
            if (flowNodeGraphicInfo.getX() < minX) {
                minX = flowNodeGraphicInfo.getX();
            }
            // 高度
            if (flowNodeGraphicInfo.getY() + flowNodeGraphicInfo.getHeight() > maxY) {
                maxY = flowNodeGraphicInfo.getY() + flowNodeGraphicInfo.getHeight();
            }
            if (flowNodeGraphicInfo.getY() < minY) {
                minY = flowNodeGraphicInfo.getY();
            }

            for (SequenceFlow sequenceFlow : flowNode.getOutgoingFlows()) {
                List<GraphicInfo> graphicInfoList = bpmnModel.getFlowLocationGraphicInfo(sequenceFlow.getId());
                if (graphicInfoList != null) {
                    for (GraphicInfo graphicInfo : graphicInfoList) {
                        // width
                        if (graphicInfo.getX() > maxX) {
                            maxX = graphicInfo.getX();
                        }
                        if (graphicInfo.getX() < minX) {
                            minX = graphicInfo.getX();
                        }
                        // height
                        if (graphicInfo.getY() > maxY) {
                            maxY = graphicInfo.getY();
                        }
                        if (graphicInfo.getY() < minY) {
                            minY = graphicInfo.getY();
                        }
                    }
                }
            }
        }

        List<Artifact> artifacts = gatherAllArtifacts(bpmnModel);
        for (Artifact artifact : artifacts) {

            GraphicInfo artifactGraphicInfo = bpmnModel.getGraphicInfo(artifact.getId());

            if (artifactGraphicInfo != null) {
                // 宽度
                if (artifactGraphicInfo.getX() + artifactGraphicInfo.getWidth() > maxX) {
                    maxX = artifactGraphicInfo.getX() + artifactGraphicInfo.getWidth();
                }
                if (artifactGraphicInfo.getX() < minX) {
                    minX = artifactGraphicInfo.getX();
                }
                // 高度
                if (artifactGraphicInfo.getY() + artifactGraphicInfo.getHeight() > maxY) {
                    maxY = artifactGraphicInfo.getY() + artifactGraphicInfo.getHeight();
                }
                if (artifactGraphicInfo.getY() < minY) {
                    minY = artifactGraphicInfo.getY();
                }
            }

            List<GraphicInfo> graphicInfoList = bpmnModel.getFlowLocationGraphicInfo(artifact.getId());
            if (graphicInfoList != null) {
                for (GraphicInfo graphicInfo : graphicInfoList) {
                    // 宽度
                    if (graphicInfo.getX() > maxX) {
                        maxX = graphicInfo.getX();
                    }
                    if (graphicInfo.getX() < minX) {
                        minX = graphicInfo.getX();
                    }
                    // 高度
                    if (graphicInfo.getY() > maxY) {
                        maxY = graphicInfo.getY();
                    }
                    if (graphicInfo.getY() < minY) {
                        minY = graphicInfo.getY();
                    }
                }
            }
        }

        int nrOfLanes = 0;
        for (Process process : bpmnModel.getProcesses()) {
            for (Lane l : process.getLanes()) {

                nrOfLanes++;

                GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(l.getId());
                // 宽度
                if (graphicInfo.getX() + graphicInfo.getWidth() > maxX) {
                    maxX = graphicInfo.getX() + graphicInfo.getWidth();
                }
                if (graphicInfo.getX() < minX) {
                    minX = graphicInfo.getX();
                }
                // 高度
                if (graphicInfo.getY() + graphicInfo.getHeight() > maxY) {
                    maxY = graphicInfo.getY() + graphicInfo.getHeight();
                }
                if (graphicInfo.getY() < minY) {
                    minY = graphicInfo.getY();
                }
            }
        }

        // Special case, see http://jira.codehaus.org/browse/ACT-1431
        if (flowNodes.isEmpty() && bpmnModel.getPools().isEmpty() && nrOfLanes == 0) {
            // Nothing to show
            minX = 0;
            minY = 0;
        }

        return new CustomProcessDiagramCanvas((int) maxX + 10, (int) maxY + 10, (int) minX, (int) minY, imageType,
                activityFontName, labelFontName, customClassLoader);
    }

    protected static List<Artifact> gatherAllArtifacts(BpmnModel bpmnModel) {
        List<Artifact> artifacts = new ArrayList<Artifact>();
        for (Process process : bpmnModel.getProcesses()) {
            artifacts.addAll(process.getArtifacts());
        }
        return artifacts;
    }

    protected static List<FlowNode> gatherAllFlowNodes(BpmnModel bpmnModel) {
        List<FlowNode> flowNodes = new ArrayList<FlowNode>();
        for (Process process : bpmnModel.getProcesses()) {
            flowNodes.addAll(gatherAllFlowNodes(process));
        }
        return flowNodes;
    }

    protected static List<FlowNode> gatherAllFlowNodes(FlowElementsContainer flowElementsContainer) {
        List<FlowNode> flowNodes = new ArrayList<FlowNode>();
        for (FlowElement flowElement : flowElementsContainer.getFlowElements()) {
            if (flowElement instanceof FlowNode) {
                flowNodes.add((FlowNode) flowElement);
            }
            if (flowElement instanceof FlowElementsContainer) {
                flowNodes.addAll(gatherAllFlowNodes((FlowElementsContainer) flowElement));
            }
        }
        return flowNodes;
    }

    public Map<Class<? extends BaseElement>, ActivityDrawInstruction> getActivityDrawInstructions() {
        return activityDrawInstructions;
    }

    public void setActivityDrawInstructions(
            Map<Class<? extends BaseElement>, ActivityDrawInstruction> activityDrawInstructions) {
        this.activityDrawInstructions = activityDrawInstructions;
    }

    public Map<Class<? extends BaseElement>, ArtifactDrawInstruction> getArtifactDrawInstructions() {
        return artifactDrawInstructions;
    }

    public void setArtifactDrawInstructions(
            Map<Class<? extends BaseElement>, ArtifactDrawInstruction> artifactDrawInstructions) {
        this.artifactDrawInstructions = artifactDrawInstructions;
    }

    protected interface ActivityDrawInstruction {

        void draw(CustomProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode);
    }

    protected interface ArtifactDrawInstruction {

        void draw(CustomProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, Artifact artifact);
    }


    /**
     * Desc: 自定义生成Diagram方法，添加对正在执行中的节点红色高亮显示
     *
     * @param bpmnModel
     * @param imageType
     * @param highLightedActivities
     * @param runningActivitiIdList
     * @param highLightedFlows
     * @param activityFontName
     * @param labelFontName
     * @param annotationFontName
     * @param customClassLoader
     * @param scaleFactor
     * @return
     * @author Fuxs
     */
    public InputStream generateDiagramCustom(BpmnModel bpmnModel, String imageType, List<String> highLightedActivities,
                                             List<String> runningActivitiIdList, List<String> highLightedFlows, String activityFontName,
                                             String labelFontName, String annotationFontName, ClassLoader customClassLoader, double scaleFactor) {
        // TODO Auto-generated method stub
        return generateProcessDiagram(bpmnModel, imageType, highLightedActivities, runningActivitiIdList,
                highLightedFlows, activityFontName, labelFontName, customClassLoader, scaleFactor, true).generateImage(
                imageType);
    }

    @Override
    public InputStream generateDiagram(BpmnModel bpmnModel, String imageType, String activityFontName,
                                       String labelFontName, String annotationFontName, ClassLoader customClassLoader, boolean drawSequenceFlowNameWithNoLabelDI) {
        return generateDiagram(bpmnModel, imageType, Collections.<String>emptyList(), Collections.<String>emptyList(),
                activityFontName, labelFontName, customClassLoader, 1.0, drawSequenceFlowNameWithNoLabelDI);
    }

    @Override
    public InputStream generateDiagram(BpmnModel bpmnModel, String imageType, String activityFontName,
                                       String labelFontName, String annotationFontName, ClassLoader customClassLoader, double scaleFactor, boolean drawSequenceFlowNameWithNoLabelDI) {
        // TODO Auto-generated method stub
        return generateDiagram(bpmnModel, imageType, Collections.<String>emptyList(), Collections.<String>emptyList(),
                activityFontName, labelFontName, customClassLoader, scaleFactor, drawSequenceFlowNameWithNoLabelDI);
    }

    @Override
    public InputStream generatePngDiagram(BpmnModel bpmnModel, boolean drawSequenceFlowNameWithNoLabelDI) {
        return generateDiagram(bpmnModel, "png", Collections.<String>emptyList(), Collections.<String>emptyList(), drawSequenceFlowNameWithNoLabelDI);
    }

    @Override
    public InputStream generateDiagram(BpmnModel bpmnModel,
                                       String imageType,
                                       List<String> highLightedActivities,
                                       List<String> highLightedFlows,
                                       String activityFontName,
                                       String labelFontName,
                                       String annotationFontName,
                                       ClassLoader customClassLoader,
                                       double scaleFactor,
                                       boolean drawSequenceFlowNameWithNoLabelDI) {
        // TODO Auto-generated method stub
        return generateProcessDiagram(bpmnModel, imageType, highLightedActivities, new ArrayList<String>(),
                highLightedFlows, activityFontName, labelFontName, customClassLoader, scaleFactor
                , drawSequenceFlowNameWithNoLabelDI).generateImage(
                imageType);
    }

    @Override
    public BufferedImage generatePngImage(BpmnModel bpmnModel, double scaleFactor) {
        return generateImage(bpmnModel, "png", Collections.<String>emptyList(), Collections.<String>emptyList(),
                scaleFactor, true);
    }

}