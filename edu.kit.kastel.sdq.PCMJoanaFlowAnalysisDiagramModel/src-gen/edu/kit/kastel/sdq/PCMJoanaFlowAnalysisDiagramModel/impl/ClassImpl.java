/**
 */
package edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl;

import edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Method;
import edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.PCMJoanaFlowAnalysisDiagramModelPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Class</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.ClassImpl#getMethod <em>Method</em>}</li>
 *   <li>{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.ClassImpl#getName <em>Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ClassImpl extends MinimalEObjectImpl.Container
		implements edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Class {
	/**
	 * The cached value of the '{@link #getMethod() <em>Method</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMethod()
	 * @generated
	 * @ordered
	 */
	protected EList<Method> method;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ClassImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PCMJoanaFlowAnalysisDiagramModelPackage.Literals.CLASS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Method> getMethod() {
		if (method == null) {
			method = new EObjectContainmentEList<Method>(Method.class, this,
					PCMJoanaFlowAnalysisDiagramModelPackage.CLASS__METHOD);
		}
		return method;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PCMJoanaFlowAnalysisDiagramModelPackage.CLASS__NAME,
					oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case PCMJoanaFlowAnalysisDiagramModelPackage.CLASS__METHOD:
			return ((InternalEList<?>) getMethod()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case PCMJoanaFlowAnalysisDiagramModelPackage.CLASS__METHOD:
			return getMethod();
		case PCMJoanaFlowAnalysisDiagramModelPackage.CLASS__NAME:
			return getName();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case PCMJoanaFlowAnalysisDiagramModelPackage.CLASS__METHOD:
			getMethod().clear();
			getMethod().addAll((Collection<? extends Method>) newValue);
			return;
		case PCMJoanaFlowAnalysisDiagramModelPackage.CLASS__NAME:
			setName((String) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case PCMJoanaFlowAnalysisDiagramModelPackage.CLASS__METHOD:
			getMethod().clear();
			return;
		case PCMJoanaFlowAnalysisDiagramModelPackage.CLASS__NAME:
			setName(NAME_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case PCMJoanaFlowAnalysisDiagramModelPackage.CLASS__METHOD:
			return method != null && !method.isEmpty();
		case PCMJoanaFlowAnalysisDiagramModelPackage.CLASS__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //ClassImpl
