/**
 */
package edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl;

import edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Assembly;
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
 * An implementation of the model object '<em><b>Assembly</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.AssemblyImpl#getClass_ <em>Class</em>}</li>
 *   <li>{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.AssemblyImpl#getName <em>Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AssemblyImpl extends MinimalEObjectImpl.Container implements Assembly {
	/**
	 * The cached value of the '{@link #getClass_() <em>Class</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClass_()
	 * @generated
	 * @ordered
	 */
	protected EList<edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Class> class_;

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
	protected AssemblyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PCMJoanaFlowAnalysisDiagramModelPackage.Literals.ASSEMBLY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Class> getClass_() {
		if (class_ == null) {
			class_ = new EObjectContainmentEList<edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Class>(
					edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Class.class, this,
					PCMJoanaFlowAnalysisDiagramModelPackage.ASSEMBLY__CLASS);
		}
		return class_;
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
			eNotify(new ENotificationImpl(this, Notification.SET,
					PCMJoanaFlowAnalysisDiagramModelPackage.ASSEMBLY__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case PCMJoanaFlowAnalysisDiagramModelPackage.ASSEMBLY__CLASS:
			return ((InternalEList<?>) getClass_()).basicRemove(otherEnd, msgs);
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
		case PCMJoanaFlowAnalysisDiagramModelPackage.ASSEMBLY__CLASS:
			return getClass_();
		case PCMJoanaFlowAnalysisDiagramModelPackage.ASSEMBLY__NAME:
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
		case PCMJoanaFlowAnalysisDiagramModelPackage.ASSEMBLY__CLASS:
			getClass_().clear();
			getClass_()
					.addAll((Collection<? extends edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Class>) newValue);
			return;
		case PCMJoanaFlowAnalysisDiagramModelPackage.ASSEMBLY__NAME:
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
		case PCMJoanaFlowAnalysisDiagramModelPackage.ASSEMBLY__CLASS:
			getClass_().clear();
			return;
		case PCMJoanaFlowAnalysisDiagramModelPackage.ASSEMBLY__NAME:
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
		case PCMJoanaFlowAnalysisDiagramModelPackage.ASSEMBLY__CLASS:
			return class_ != null && !class_.isEmpty();
		case PCMJoanaFlowAnalysisDiagramModelPackage.ASSEMBLY__NAME:
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

} //AssemblyImpl
