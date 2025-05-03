import React from 'react';
import { Translate, translate, ValidatedField } from 'react-jhipster';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter, Alert, Row, Col, Form } from 'reactstrap';
import { Link } from 'react-router-dom';
import { type FieldError, useForm } from 'react-hook-form';

export interface ICategorieModalProps {
  showModal: boolean;
  saveCategory: (categoryName: string) => void;
  handleClose: () => void;
}
 
const CategorieModal = (props: ICategorieModalProps) => {
  const saveCategory = ({ categoryName }) => {
    props.saveCategory(categoryName);
  };

  const {
    handleSubmit,
    register,
    formState: { errors, touchedFields },
  } = useForm({ mode: 'onTouched' });

  const { handleClose } = props;

  const handleSaveCategory = e => {
    handleSubmit(saveCategory)(e);
  };

  return (
    <Modal isOpen={props.showModal} toggle={handleClose} backdrop="static" id="categorie-modal" autoFocus={false}>
      <Form onSubmit={handleSaveCategory}>
        <ModalHeader id="categorie-title" data-cy="categorieTitle" toggle={handleClose}>
          <Translate contentKey="categorie.form.title">Add Category</Translate>
        </ModalHeader>
        <ModalBody>
          <Row>
            <Col md="12">
              <ValidatedField
                name="categoryName"
                label={translate('categorie.form.categoryName.label')}
                placeholder={translate('categorie.form.categoryName.placeholder')}
                required
                autoFocus
                data-cy="categoryName"
                validate={{ required: 'Category Name cannot be empty!' }}
                register={register}
                error={errors.categoryName as FieldError}
                isTouched={touchedFields.categoryName}
              />
            </Col>
          </Row>
        </ModalBody>
        <ModalFooter>
          <Button color="secondary" onClick={handleClose} tabIndex={1}>
            <Translate contentKey="entity.action.cancel">Cancel</Translate>
          </Button>{' '}
          <Button color="primary" type="submit" data-cy="submit">
            <Translate contentKey="categorie.form.button">Save Category</Translate>
          </Button>
        </ModalFooter>
      </Form>
    </Modal>
  );
};

export default CategorieModal;
